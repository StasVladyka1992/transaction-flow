package com.gamingtec.services.bonus.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BonusBalanceRoute extends RouteBuilder {
  @Override
  public void configure() {
    from("kafka:balanceRequest?brokers=localhost:9095")
        .bean("bonusBalanceService", "getBonusBalance");
  }
}
