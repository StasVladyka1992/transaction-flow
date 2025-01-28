package com.gamingtec.services.wallet.route;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;
import static com.gamingtec.services.event.route.RouteNames.DIRECT_GRPC_BALANCE_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.DIRECT_GRPC_BET_REQUEST;

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
//    from("grpc://localhost:9899/com.gamingtec.wallet.WalletApi?consumerStrategy=PROPAGATION")
    from("grpc://localhost:9899/com.gamingtec.wallet.WalletApi?consumerStrategy=PROPAGATION")
        .setHeader(CORRELATION_ID, () -> UUID.randomUUID().toString())
        .choice()
        .when(header("CamelGrpcMethodName").isEqualTo("balance"))
        .log("Grpc balance request received, correlationId: ${header.correlationId}")
        .bean(balanceRequestMapper, "toBalanceRequestEvent")
        .to(DIRECT_GRPC_BALANCE_REQUEST)
        .bean(balanceMapper, "toBalanceGrpc")
        .log("Balance was sent by grpc, correlationId: ${header.correlationId}")

        .when(header("CamelGrpcMethodName").isEqualTo("bet"))
        .log("Grpc bet request received, correlationId: ${header.correlationId}")
        .bean(betRequestMapper, "toBetRequest")
        .to(DIRECT_GRPC_BET_REQUEST)
        .bean(betResponseMapper, "toGrpc")
        .log("Bet response was sent by grpc, correlationId: ${header.correlationId}");
    //TODO use grpc marshall and unmarshal functionality to marshall and unmarshall automatically
    // .marshal().protobuf(MyProtoMessage.class)  //
  }
}
