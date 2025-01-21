package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.wallet.route.mapper.BalanceMapper;
import com.gamingtec.services.wallet.route.mapper.BalanceRequestMapper;
import com.gamingtec.services.wallet.route.mapper.BetRequestMapper;
import com.gamingtec.services.wallet.route.mapper.BetResponseMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcWalletRoutes extends RouteBuilder {
  private final BalanceRequestMapper balanceRequestMapper;
  private final BetRequestMapper betRequestMapper;
  private final BalanceMapper balanceMapper;
  private final BetResponseMapper betResponseMapper;


  @Override
  public void configure() throws Exception {
    from("grpc://localhost:9899/com.gamingtec.wallet.WalletApi?consumerStrategy=PROPAGATION")
        .setHeader(CORRELATION_ID, () -> UUID.randomUUID().toString())
        .choice()

        .when(header("CamelGrpcMethodName").isEqualTo("balance"))
        .log("Grpc balance request received")
        .bean(balanceRequestMapper, "toBalanceRequestEvent")
        .to("direct:grpcBalanceRequest")
        .bean(balanceMapper, "toBalanceGrpc")
        .log("Balance was sent by grpc")

        .when(header("CamelGrpcMethodName").isEqualTo("bet"))
        .log("Grpc bet request received")
        .bean(betRequestMapper, "toBetRequestEvent")
        .to("direct:grpcBetRequest")
        .bean(betResponseMapper, "toGrpc")
        .log("Bet response was sent by grpc");
  }
}
