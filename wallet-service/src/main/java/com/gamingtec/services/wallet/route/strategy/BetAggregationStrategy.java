package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BetResponseEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BetAggregationStrategy implements AggregationStrategy {
  private final Map<String, BetResponseEvent> aggregatedResponses = new ConcurrentHashMap<>();

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    String correlationId = getCorrelationId(newExchange);
    Object newBody = newExchange.getIn().getBody();
    Object oldBody = oldExchange.getIn().getBody();
    handleBody(correlationId, newBody, oldBody);
    return newExchange;
  }

  private void handleBody(String correlationId, Object newBody, Object oldBody) {
    BetResponseEvent result = aggregatedResponses.computeIfAbsent(correlationId, k -> new BetResponseEvent());
    if (result == null) {
      BetBucket betBucket = (BetBucket) oldBody;
      BalanceEvent balanceEvent = BalanceEvent.builder()
          .accountId(betBucket.getPartyId()) //TODO change
          .partyId(betBucket.getPartyId())
          .currency(betBucket.getCurrency())
          .build();
      result = BetResponseEvent.builder()
          .balance(balanceEvent)
          .transactionId(1L) //TODO change
          .build();
    }

    if (newBody instanceof BonusBalanceEvent) {
      BonusBalanceEvent bonusBalanceEvent = (BonusBalanceEvent) newBody;
      result.getBalance().setReleasedBonus(bonusBalanceEvent.getReleasedBonus());
      result.getBalance().setPlayableBonus(bonusBalanceEvent.getPlayableBonus());
    } else if (newBody instanceof CashBalanceEvent) {
      CashBalanceEvent cashBalanceEvent = (CashBalanceEvent) newBody;
      result.getBalance().setReal(cashBalanceEvent.getReal());
    }
  }

  @Override
  public void onCompletion(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    BetResponseEvent response = aggregatedResponses.get(correlationId);
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
