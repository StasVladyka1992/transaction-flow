package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Slf4j
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
    response.setCurrency("USD");
    response.setPartyId(1);
    response.setAccountId(1);

    if (body instanceof BonusBalanceEvent) {
      BonusBalanceEvent bonusBalanceEvent = (BonusBalanceEvent) body;
      response.setReleasedBonus(bonusBalanceEvent.getReleasedBonus());
      response.setPlayableBonus(bonusBalanceEvent.getPlayableBonus());
    } else if (body instanceof CashBalanceEvent) {
      CashBalanceEvent cashBalanceEvent = (CashBalanceEvent) body;
      response.setReal(cashBalanceEvent.getReal());
    }
//    else if (body instanceof LoyaltyBalanceResponse) {
//      response.setLoyaltyPoints(((LoyaltyBalanceResponse) body).getLoyaltyBalance());
//    }

  }

  @Override
  public void onCompletion(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    BalanceEvent response = aggregatedResponses.get(correlationId);
    log.info("Received response for correlationId: {} is {}", correlationId, response);
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
