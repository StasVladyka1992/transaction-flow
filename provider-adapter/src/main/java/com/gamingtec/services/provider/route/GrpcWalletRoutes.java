package com.gamingtec.services.provider.route;

import com.gamingtec.services.provider.route.mapper.BalanceMapper;
import com.gamingtec.services.provider.route.mapper.BalanceRequestMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcWalletRoutes extends RouteBuilder {
  private final BalanceRequestMapper balanceRequestMapper;
  private final BalanceMapper balanceMapper;

  @Override
  public void configure() {
    from("direct:grpcBalance")
        .log("Http request received: ${body}")
        .bean(balanceRequestMapper, "toGrpc")
        .log("Grpc request is sending...")
        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
            + "&synchronous=true"
            + "&streamRepliesTo=direct:grpcBalance")
        .log("Balance recieved: ${body}")
        .bean(balanceMapper, "toDto")
        .removeHeaders("*", "Content-Type")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
  }
}
