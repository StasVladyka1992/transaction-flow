package com.gamingtec.services.provider.route.route;

import com.gamingtec.services.provider.route.route.transformer.MapperToGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class WalletBalanceRouteConfig extends RouteBuilder {
  private final MapperToGrpc mapperToGrpc;

  @Override
  public void configure() {

    from("direct:balance")
//    from("rest:get:camel/wallet/balance")
//    from("timer://grpc-timer?period=10000")
        .log("Received balance request: ${body}")
        .bean(mapperToGrpc)
//        .convertBodyTo(String.class) - doesn't work for some reasons
//        .setBody(constant(WalletMessages.BalanceRequestGrpc.getDefaultInstance()))
        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
            + "&synchronous=true"
            + "&streamRepliesTo=direct:walletBalanceResponse")
        .log("Response from walletBalanceResponse: ${body}");

//        .split(body()) // The response is a stream, so we split it into individual Account objects
//        .log("Received account details")
//        .process(exchange -> {
//          // Extract account details from the response
//          var res = exchange.getIn().getBody(Empty.class);
//          log.info("this is response from grpc: {}", res);
//        });
  }
}