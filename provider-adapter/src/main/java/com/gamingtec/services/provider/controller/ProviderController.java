package com.gamingtec.services.provider.controller;

import com.gamingtec.services.provider.controller.dto.BalanceDto;
import com.gamingtec.services.provider.controller.dto.BalanceReqDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ProviderController extends RouteBuilder {

  //Config 1
  @Override
  public void configure() {
    restConfiguration()
////        .apiContextRouteId("swagger")
//        .apiContextPath("api-doc")
//        .apiProperty("api.title", "REST API for processing Order")
//        .apiProperty("api.version", "1.0")


        // Use the 'servlet' component.
        // This tells Camel to create and use a Servlet to 'host' the RESTful API.
        // Since we're using Spring Boot, the default servlet container is Tomcat.
//        .component("servlet")
//        // Allow Camel to try to marshal/unmarshal between Java objects and JSON
//        .port(8080)
//        .dataFormatProperty("prettyPrint", "true")
//        .bindingMode(RestBindingMode.auto)
//
//        .contextPath("/api")  // Base path for your API
//        .apiContextPath("/openapi")  // Path to OpenAPI documentation
//        .apiProperty("api.title", "Example API")
//        .apiProperty("api.version", "1.0")
//        .apiProperty("cors", "true")  // Enable CORS
//        .apiProperty("host", "localhost:8080")  // Optional, specify host
//        .apiProperty("api.description", "This is an example API")
//        .apiProperty("api.contact.name", "Support Team")
//        .apiProperty("api.contact.email", "support@example.com")
//        .apiContextRouteId("openapi");


//    restConfiguration()
        .component("servlet")
        .bindingMode(RestBindingMode.json)
        .dataFormatProperty("prettyPrint", "true")
        .enableCORS(true)
        .port(8080)
        //http://localhost:8080/swagger-ui/index.html#/
//        .contextPath("/camel")
        // turn on openapi api-doc, http://localhost:8080/api/api-doc
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "User API")
        .apiProperty("api.version", "1.0.0");


    rest("/wallet")
        .get("/test-balance").description("Get test player balance")
        .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
        .consumes(MediaType.APPLICATION_JSON_VALUE)
        .produces(MediaType.APPLICATION_JSON_VALUE)
        .type(BalanceReqDto.class)
        .outType(BalanceDto.class)
        .to("direct:getTestBalance")

          .get("/balance").description("Get player balance")
          .consumes(MediaType.APPLICATION_JSON_VALUE)
          .produces(MediaType.APPLICATION_JSON_VALUE)
          .type(BalanceReqDto.class)
          .outType(BalanceDto.class)
          .to("direct:getBalance");
//          .to("grpc://127.0.0.1:9899/com.gamingtec.wallet.WalletApi?method=balanceRequest"
//              + "&synchronous=true"
//              + "&streamRepliesTo=direct:walletBalanceResponse");
  }

  //Config 2
//  @GetMapping("camel/balance")
//  public BalanceDto getCamelBalance(@RequestBody BalanceReqDto dto) {
//    WalletMessages.BalanceGrpc resp = producerTemplate.requestBody("direct:balance-request", dto, WalletMessages.BalanceGrpc.class);
//    return new BalanceDto();
//  }
}
