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
public class RestWalletRoutes extends RouteBuilder {
  @Override
  public void configure() {
    restConfiguration()
        .component("servlet")
        .dataFormatProperty("prettyPrint", "true")
        .bindingMode(RestBindingMode.json)
        .enableCORS(true)
        .port(8080)
        //http://localhost:8080/swagger-ui/index.html#/
        // turn on openapi api-doc, http://localhost:8080/{camel.servlet.mapping.context.path}/api-doc
        .apiContextRouteId("swagger")
//       path to swagger yml. doc
//        .apiContextPath("/camel*")
        //context path when trigger calls inside camel
        .contextPath("/camel")
        .apiProperty("api.title", "User API")
        .apiProperty("api.version", "1.0.0");

    rest("/wallet")
        .post("/balance").description("get player balance")
//        .param().name("partyId").type(RestParamType.query).required(true).endParam()
        .consumes(MediaType.APPLICATION_JSON_VALUE)
        .produces(MediaType.APPLICATION_JSON_VALUE)
        .type(BalanceReqDto.class)
        .outType(BalanceDto.class)
        //        .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
        .to("direct:grpcBalance");
  }
}
