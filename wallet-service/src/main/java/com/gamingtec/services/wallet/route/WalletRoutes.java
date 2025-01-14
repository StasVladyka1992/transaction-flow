package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.AbstractBalanceEvent;
import com.gamingtec.services.wallet.route.mapper.BalanceRequestEventMapper;
import com.gamingtec.services.wallet.route.mapper.ToBalanceGrpcEventMapper;
import com.gamingtec.services.wallet.route.strategy.BalanceAggregationStrategy;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.grpc.springboot.GrpcComponentConfiguration;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletRoutes extends RouteBuilder {
  private final BalanceAggregationStrategy balanceAggregationStrategy;
  private final BalanceRequestEventMapper balanceRequestEventMapper;
  private final ToBalanceGrpcEventMapper toBalanceGrpcEventMapper;
  private final GrpcComponentConfiguration grpcComponentConfiguration;

  @Override
  public void configure() {

    from(
        "grpc://localhost:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest?consumerStrategy=PROPAGATION") //TODO check
        .setHeader(CORRELATION_ID, () -> UUID.randomUUID().toString())
        .log("Grpc request received")
//        .threads().poolSize(50).maxQueueSize(200).maxPoolSize(100)
        .bean(balanceRequestEventMapper)
        .marshal().json(JsonLibrary.Jackson)
        .process(exchange -> log.info("Balance request to kafka will be sent, headers: {}",
            exchange.getIn().getHeader(CORRELATION_ID)))
        .to("kafka:balanceRequest?brokers=localhost:9095")
        .pollEnrich("direct:aggregatedBalance", 1000)
        .log("Total balance: ${body}")
        .bean(toBalanceGrpcEventMapper)
        .log("Balance was sent by grpc");

    from("kafka:balance?brokers=localhost:9095")
        .unmarshal().json(JsonLibrary.Jackson, AbstractBalanceEvent.class)
//        .process(exchange -> log.info("Balance response from kafka received, headers: {}",
//            new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8)))
        .aggregate(header(CORRELATION_ID), balanceAggregationStrategy)
        .completionSize(2)
        .completionTimeout(1000)
        .executorService("executorService")
        .process(exchange -> log.info("Balance response from kafka was aggregated, headers: {}",
            new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8)))
        .to("direct:aggregatedBalance");
  }
}
