package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;
import static com.gamingtec.services.wallet.route.util.RouteNames.DIRECT_GRPC_BALANCE_REQUEST;
import static com.gamingtec.services.wallet.route.util.RouteNames.DIRECT_GRPC_BET_REQUEST;
import static com.gamingtec.services.wallet.route.util.RouteNames.KAFKA_BALANCE;
import static com.gamingtec.services.wallet.route.util.RouteNames.KAFKA_BALANCE_REQUEST;
import static com.gamingtec.services.wallet.route.util.RouteNames.KAFKA_BET_REQUEST;
import static com.gamingtec.services.wallet.route.util.RouteNames.SEDA_AGGREGATED_BALANCE;

import com.gamingtec.services.event.dto.AbstractBalanceEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.strategy.BalanceAggregationStrategy;
import com.gamingtec.services.wallet.route.strategy.BetBucketAggregationStrategy;
import com.gamingtec.services.wallet.service.TransactionService;
import com.gamingtec.services.wallet.service.WalletService;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletRoutes extends RouteBuilder {


  private final BalanceAggregationStrategy balanceAggregationStrategy;
  private final BetBucketAggregationStrategy betBucketAggregationStrategy;
  private final WalletService walletService;
  private final TransactionService transactionService;
  private final ExecutorService executorService;

  @Override
  public void configure() {
    //balance route
    from(DIRECT_GRPC_BALANCE_REQUEST)
        .marshal().json(JsonLibrary.Jackson)
        .process(exchange -> log.info("Balance request to kafka will be sent, body: {}, headers: {}",
            exchange.getIn().getBody(), exchange.getIn().getHeader(CORRELATION_ID)))
        .to(KAFKA_BALANCE_REQUEST)
        .pollEnrich(SEDA_AGGREGATED_BALANCE, 1000)
        .log("Result balance: ${body}");

//    //bet route
    from(DIRECT_GRPC_BET_REQUEST)
        .bean(walletService, "initGetBalance")
        .pollEnrich(SEDA_AGGREGATED_BALANCE, 1000, betBucketAggregationStrategy)
        .log("Bet bucket : ${body}")
        .bean(walletService, "bet")
        .marshal().json(JsonLibrary.Jackson, BetRequestEvent.class)
        .log("Bet request will be send : ${body}")
        .to(KAFKA_BET_REQUEST)
        .pollEnrich(SEDA_AGGREGATED_BALANCE, balanceAggregationStrategy)
        .bean(transactionService, "createBetTransaction")
        .log("Bet response: ${body}");

    from(KAFKA_BALANCE)
        .unmarshal().json(JsonLibrary.Jackson, AbstractBalanceEvent.class)
        .aggregate(header(CORRELATION_ID), balanceAggregationStrategy)
        .completionSize(2)
        .completionTimeout(1000)
        .executorService(executorService)
        .process(exchange -> log.info("Balance response from kafka was aggregated, headers: {}",
            new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8)))
        .to(SEDA_AGGREGATED_BALANCE);
  }
}
