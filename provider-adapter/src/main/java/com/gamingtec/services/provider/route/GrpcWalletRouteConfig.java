package com.gamingtec.services.provider.route;

import com.gamingtec.services.provider.route.mapper.BalanceGrpcMapper;
import com.gamingtec.services.provider.route.mapper.BalanceRequestGrpcMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcWalletRouteConfig extends RouteBuilder {
  private final BalanceRequestGrpcMapper balanceRequestGrpcMapper;
  private final BalanceGrpcMapper balanceGrpcMapper;

  @Override
  public void configure() {
    from("direct:grpcBalance")
        .bean(balanceRequestGrpcMapper)
        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
            + "&synchronous=true"
            + "&streamRepliesTo=direct:grpcBalance")
        .bean(balanceGrpcMapper); //TODO better to send messages to kafka directly (without grpc)
  }
}
