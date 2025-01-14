package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class BalanceAggregationStrategy implements AggregationStrategy {

  private final Map<String, BalanceEvent> aggregatedResponses = new ConcurrentHashMap<>();

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    String correlationId = getCorrelationId(newExchange);
    Object body = newExchange.getIn().getBody();
    handleBody(correlationId, body);
    return newExchange;
  }

  private void handleBody(String correlationId, Object body) {
    BalanceEvent response = aggregatedResponses.computeIfAbsent(correlationId, k -> new BalanceEvent());

    if (body instanceof BonusBalanceEvent) {
      response.setBonus(((BonusBalanceEvent) body).getBonusBalance());
    } else if (body instanceof CashBalanceEvent) {
      response.setCash(((CashBalanceEvent) body).getCashBalance());
    }
//    else if (body instanceof LoyaltyBalanceResponse) {
//      response.setLoyaltyPoints(((LoyaltyBalanceResponse) body).getLoyaltyBalance());
//    }
  }

  @Override
  public void onCompletion(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    BalanceEvent response = aggregatedResponses.get(correlationId);
    exchange.getMessage().setBody(response);
    releaseResources(exchange);
  }

  @Override
  public void timeout(Exchange exchange, int index, int total, long timeout) {
    releaseResources(exchange);
  }

  private void releaseResources(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    aggregatedResponses.remove(correlationId);
  }

  private String getCorrelationId(Exchange exchange) {
    return new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8);
  }
}
