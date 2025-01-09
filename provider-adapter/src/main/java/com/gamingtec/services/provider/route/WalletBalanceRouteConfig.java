package com.gamingtec.services.provider.route;

import com.gamingtec.services.provider.route.mapper.BalanceRequestGrpcMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class WalletBalanceRouteConfig extends RouteBuilder {
  private final BalanceRequestGrpcMapper balanceRequestGrpcMapper;

  @Override
  public void configure() {
//    from("rest:get:/wallet/balance")
     from("direct:getTestBalance")
        .log("Received balance request: ${body}")
        .bean(balanceRequestGrpcMapper)
//        .convertBodyTo(WalletMessages.BalanceRequestGrpc.class) - doesn't work for some reasons
        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
            + "&synchronous=true"
            + "&streamRepliesTo=direct:walletBalanceResponse")
        .log("Response from walletBalanceResponse: ${body}")
        .process(exchange -> {
          // Extract account details from the response
//          var res = exchange.getIn().getBody(Empty.class);
//          log.info("this is response from grpc: {}", res);
        });
  }

//  @Override
//  public void configure() {
//    from("rest:get:camel/wallet/balance")
//        .log("Received balance request: ${body}")
//        .bean(balanceRequestGrpcMapper)
////        .convertBodyTo(WalletMessages.BalanceRequestGrpc.class) - doesn't work for some reasons
//        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
//            + "&synchronous=true"
//            + "&streamRepliesTo=direct:walletBalanceResponse")
//        .log("Response from walletBalanceResponse: ${body}")
////        .split(body()) // The response is a stream, so we split it into individual Account objects
////        .log("Received account details")
//        .process(exchange -> {
//          // Extract account details from the response
////          var res = exchange.getIn().getBody(Empty.class);
////          log.info("this is response from grpc: {}", res);
//        });
//  }
}