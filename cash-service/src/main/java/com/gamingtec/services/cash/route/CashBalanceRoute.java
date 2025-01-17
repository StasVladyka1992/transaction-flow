package com.gamingtec.services.cash.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamingtec.services.cash.service.CashBalanceService;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashBalanceRoute extends RouteBuilder {
  private final CashBalanceService cashBalanceService;

  @Override
  public void configure() {
    from("kafka:balanceRequest?brokers=localhost:9095")
        .log("Received balance event: ${body}")
        .unmarshal().json(JsonLibrary.Jackson, BalanceRequestEvent.class)
        .log("Parsed balance request: ${body}")
        .bean(cashBalanceService, "getCashBalance")
        .log("Cash balance: ${body}")
        .marshal().json(JsonLibrary.Jackson)
        .to("kafka:balance?brokers=localhost:9095")
        .log("Cash balance was sent");
  }
}
