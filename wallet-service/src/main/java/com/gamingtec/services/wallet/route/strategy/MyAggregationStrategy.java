package com.gamingtec.services.wallet.route.strategy;

import com.gamingtec.services.event.dto.BalanceEvent;
import java.math.BigDecimal;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MyAggregationStrategy implements AggregationStrategy {
  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    if (oldExchange == null) {
      return newExchange;
    }

    String oldBody = oldExchange.getIn().getBody(String.class);
    String newBody = newExchange.getIn().getBody(String.class);
    oldExchange.getIn().setBody(BalanceEvent.builder()
        .partyId(1)
        .balance(new BigDecimal("100"))
        .build());
    return oldExchange;
  }
}
