package com.gamingtec.services.provider.route;

import com.gamingtec.services.provider.route.mapper.BalanceGrpcMapper;
import com.gamingtec.services.provider.route.mapper.BalanceRequestGrpcMapper;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcWalletRoutes extends RouteBuilder {
  private final BalanceRequestGrpcMapper balanceRequestGrpcMapper;
  private final BalanceGrpcMapper balanceGrpcMapper;

  @Override
  public void configure() {
    from("direct:grpcBalance")
        .log("Http request received: ${body}")
        .process(exchange -> exchange.getMessage().setBody(WalletMessages.BalanceRequestGrpc.getDefaultInstance()))
        .log("Grpc request is sending...")
        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
            + "&synchronous=true"
            + "&streamRepliesTo=direct:grpcBalance")
        .bean(balanceGrpcMapper)
        .removeHeaders("*", "Content-Type")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
  }
}
