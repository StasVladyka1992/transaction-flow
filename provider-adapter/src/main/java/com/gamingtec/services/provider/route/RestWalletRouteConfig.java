package com.gamingtec.services.provider.route;

import com.gamingtec.services.provider.route.dto.BalanceDto;
import com.gamingtec.services.provider.route.dto.BalanceReqDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RestWalletRouteConfig extends RouteBuilder {

  //Config 1
  @Override
  public void configure() {
    restConfiguration()
        .component("servlet")
        .bindingMode(RestBindingMode.json)
        .dataFormatProperty("prettyPrint", "true")
        .enableCORS(true)
        .port(8080)
        //http://localhost:8080/swagger-ui/index.html#/
        // turn on openapi api-doc, http://localhost:8080/{camel.servlet.mapping.context.path}/api-doc
        .apiContextRouteId("swagger")
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "User API")
        .apiProperty("api.version", "1.0.0");


    rest("/wallet")
        .get("/balance").description("Get player balance")
        .routeId("restBalanceRequest")
        .consumes(MediaType.APPLICATION_JSON_VALUE)
        .produces(MediaType.APPLICATION_JSON_VALUE)
        .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
        .type(BalanceReqDto.class)
        .outType(BalanceDto.class)
        .to("direct:grpcBalance");
//        .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
//            + "&synchronous=true"
//            + "&streamRepliesTo=direct:grpcBalance");
  }

  //Config 2
//  @GetMapping("camel/balance")
//  public BalanceDto getCamelBalance(@RequestBody BalanceReqDto dto) {
//    WalletMessages.BalanceGrpc resp = producerTemplate.requestBody("direct:balance-request", dto, WalletMessages.BalanceGrpc.class);
//    return new BalanceDto();
//  }
}
