package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.AbstractBalanceEvent;
import com.gamingtec.services.wallet.route.strategy.BalanceAggregationStrategy;
import com.gamingtec.services.wallet.route.strategy.BetAggregationStrategy;
import com.gamingtec.services.wallet.route.strategy.BetBucketAggregationStrategy;
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
  private final BetAggregationStrategy betAggregationStrategy;
  private final WalletService walletService;
  private final ExecutorService executorService;

  @Override
  public void configure() throws Exception {
    //balance route
    from("direct:grpcBalanceRequest")
        .marshal().json(JsonLibrary.Jackson)
        .process(exchange -> log.info("Balance request to kafka will be sent, headers: {}",
            exchange.getIn().getHeader(CORRELATION_ID)))
        .to("kafka:balanceRequest?brokers=localhost:9095")
        .pollEnrich("direct:aggregatedBalance", 1000)
        .log("Balance: ${body}");

    from("kafka:balance?brokers=localhost:9095")
        .unmarshal().json(JsonLibrary.Jackson, AbstractBalanceEvent.class)
        .aggregate(header(CORRELATION_ID), balanceAggregationStrategy)
        .completionSize(2)
        .completionTimeout(1000)
        .executorService("executorService")
        .process(exchange -> log.info("Balance response from kafka was aggregated, headers: {}",
            new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8)))
        .to("direct:aggregatedBalance");

    //bet route
    from("direct:grpcBetRequest")
        .bean(walletService, "initGetBalance")
        .pollEnrich("direct:aggregatedBalance", 1000, betBucketAggregationStrategy)
        .log("Bet bucket : ${body}")
        .to("kafka:betRequest?brokers=localhost:9095")
        .aggregate(header(CORRELATION_ID), betAggregationStrategy)
//        .aggregationRepository()
        .completionSize(2)
        .completionTimeout(1000)
        .log("Bet response: ${body}");
//        .executorService(executorService)
  }
}
