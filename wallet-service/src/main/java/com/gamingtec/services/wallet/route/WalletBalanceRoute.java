package com.gamingtec.services.wallet.route;

import com.gamingtec.services.wallet.route.mapper.ToBalanceRequestEventGrpcMapper;
import com.gamingtec.services.wallet.route.strategy.MyAggregationStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletBalanceRoute extends RouteBuilder {
  private final ToBalanceRequestEventGrpcMapper toBalanceRequestEventGrpcMapper;

  @Override
  public void configure() {
    from("grpc://localhost:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest?consumerStrategy=PROPAGATION") //TODO check
        .bean(toBalanceRequestEventGrpcMapper)
        .to("kafka:balanceRequest?brokers=localhost:9095")
        .aggregate(header("correlationId"), new MyAggregationStrategy())
        .completionSize(2)
        .completionTimeout(1000)
        .process();
  }
}
