package com.gamingtec.services.bonus.route;

import com.gamingtec.services.bonus.service.BonusBalanceService;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BonusBalanceRoute extends RouteBuilder {
  private final BonusBalanceService bonusBalanceService;

  @Override
  public void configure() {
    from("kafka:balanceRequest?brokers=localhost:9095")
        .log("Received balance event: ${body}")
        .unmarshal().json(JsonLibrary.Jackson, BalanceRequestEvent.class)
        .log("Parsed balance request: ${body}")
        .bean(bonusBalanceService, "getBonusBalance")
        .log("Bonus balance: ${body}")
        .marshal().json(JsonLibrary.Jackson)
        .to("kafka:balance?brokers=localhost:9095")
        .log("Bonus balance was sent");

    from("kafka:betRequest?brokers=localhost:9095")
        .log("Received bet event: ${body}")
        .unmarshal().json(JsonLibrary.Jackson, BetRequestEvent.class)
        .log("Received bet event: ${body}")
        .bean(bonusBalanceService, "bet")
        .log("Bonus balance after bet: ${body}")
        .marshal().json(JsonLibrary.Jackson)
        .to("kafka:balance?brokers=localhost:9095")
        .log("Bonus balance was sent");
  }
}
