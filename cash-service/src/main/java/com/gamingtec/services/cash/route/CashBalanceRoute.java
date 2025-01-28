package com.gamingtec.services.cash.route;

import static com.gamingtec.services.event.route.RouteNames.KAFKA_BALANCE;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BALANCE_REQUEST;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BET_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamingtec.services.cash.service.CashBalanceService;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
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
    from(KAFKA_BALANCE_REQUEST)
        .log("Received balance event, correlationId: ${headers.correlationId},  ${body}")
        .unmarshal().json(JsonLibrary.Jackson, BalanceRequestEvent.class)
        .log("Parsed balance request, correlationId: ${headers.correlationId}, ${body}")
        .bean(cashBalanceService, "getCashBalance")
        .log("Cash balance, correlationId: ${headers.correlationId}, ${body}")
        .marshal().json(JsonLibrary.Jackson)
        .to(KAFKA_BALANCE)
        .log("Cash balance was sent, correlationId: ${headers.correlationId}");

    from(KAFKA_BET_REQUEST)
        .log("Received bet event, correlationId: ${headers.correlationId}, ${body}")
        .unmarshal().json(JsonLibrary.Jackson, BetRequestEvent.class)
        .log("Received bet event, correlationId: ${headers.correlationId}, ${body}")
        .bean(cashBalanceService, "bet")
        .log("Cash balance after bet, correlationId: ${headers.correlationId}, ${body}")
        .marshal().json(JsonLibrary.Jackson)
        .to(KAFKA_BALANCE)
        .log("Cash balance was sent, correlationId: ${headers.correlationId}");
  }
}
