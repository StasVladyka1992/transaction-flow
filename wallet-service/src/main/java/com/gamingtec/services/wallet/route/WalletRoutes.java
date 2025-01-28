package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;
import static com.gamingtec.services.event.route.RouteNames.DIRECT_GRPC_BALANCE_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.DIRECT_GRPC_BET_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BALANCE;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BALANCE_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BET_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.SEDA_AGGREGATED_BALANCE;

import com.gamingtec.services.event.dto.AbstractBalanceEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.strategy.BalanceAggregationStrategy;
import com.gamingtec.services.wallet.route.strategy.BetBucketAggregationStrategy;
import com.gamingtec.services.wallet.service.TransactionServiceImpl;
import com.gamingtec.services.wallet.service.wallet.WalletService;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
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
  private final TransactionServiceImpl transactionServiceImpl;
  private final ExecutorService executorService;

  @Override
  public void configure() {
    //balance route
    from(DIRECT_GRPC_BALANCE_REQUEST)
        .marshal().json(JsonLibrary.Jackson)
        .log("Balance request: correlationId: ${header.correlationId}, ${body}")
        .to(KAFKA_BALANCE_REQUEST)
        .pollEnrich(SEDA_AGGREGATED_BALANCE, 10000)
        .log("Result balance: correlationId: ${header.correlationId}, ${body}");

    //bet route
    from(DIRECT_GRPC_BET_REQUEST)
        .bean(walletService, "getBalanceForBet")
        .pollEnrich(SEDA_AGGREGATED_BALANCE, 10000, betBucketAggregationStrategy)
        .log("Bet bucket: correlationId: ${headers.correlationId}, ${body}")
        .bean(walletService, "bet")
        .marshal().json(JsonLibrary.Jackson, BetRequestEvent.class)
        .log("Bet request will be send: correlationId: ${headers.correlationId}, ${body}")
        .to(KAFKA_BET_REQUEST)
        .pollEnrich(SEDA_AGGREGATED_BALANCE, 10000)
        .bean(transactionServiceImpl, "createBetTransaction")
        .log("Bet response: correlationId: ${headers.correlationId}, ${body}");


    //0) calculate loyalty points
    //1) update account
    //2) create transaction
    //3) create game bonus bucket and save to table
    //4) wager - 1) change account balance
    //           2) create transaction

    from(KAFKA_BALANCE)
        .unmarshal().json(JsonLibrary.Jackson, AbstractBalanceEvent.class)
        .aggregate(header(CORRELATION_ID), balanceAggregationStrategy)
        .completionTimeout(10000)
        .completionPredicate(exchangeProperty(Exchange.AGGREGATION_COMPLETE_ALL_GROUPS_INCLUSIVE).isEqualTo(true))
        .executorService(executorService)
        .log("Balance response from kafka was aggregated, correlationId: ${headers.correlationId}, ${body}")
        .to(SEDA_AGGREGATED_BALANCE);
  }
}
