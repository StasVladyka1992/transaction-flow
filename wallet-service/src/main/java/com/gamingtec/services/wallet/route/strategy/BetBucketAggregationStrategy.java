package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
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
public class BetBucketAggregationStrategy implements AggregationStrategy {
  private final Map<String, BetBucket> aggregatedResponses = new ConcurrentHashMap<>();

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    String correlationId = getCorrelationId(newExchange);
    Object newBody = newExchange.getIn().getBody();
    Object oldBody = oldExchange.getIn().getBody();
    handleBody(correlationId, newBody, oldBody);
    return newExchange;
  }

  private void handleBody(String correlationId, Object newBody, Object oldBody) {
    BetBucket result = aggregatedResponses.computeIfAbsent(correlationId, k -> new BetBucket());
    if (result.getPartyId() == 0) {
      BetRequestEvent betRequestEvent = (BetRequestEvent) oldBody;
      result = BetBucket.builder()
          .partyId(betRequestEvent.getPartyId())
          .amount(betRequestEvent.getAmount())
          .currency(betRequestEvent.getCurrency())
          .brandId(betRequestEvent.getBrandId())
          .platformId(betRequestEvent.getPlatformId())
          .platformCode(betRequestEvent.getPlatformCode())
          .sportbook(betRequestEvent.isSportbook())
          .gameInfoId(betRequestEvent.getGameInfoId())
          .gameId(betRequestEvent.getGameId())
          .platformGameTranId(betRequestEvent.getPlatformGameTranId())
          .platformTranId(betRequestEvent.getPlatformTranId())
          .build();
    }


    if (newBody instanceof BonusBalanceEvent) {
      BonusBalanceEvent bonusBalanceEvent = (BonusBalanceEvent) newBody;
      result.setReleasedBonus(bonusBalanceEvent.getReleasedBonus());
      result.setPlayableBonus(bonusBalanceEvent.getPlayableBonus());
    } else if (newBody instanceof CashBalanceEvent) {
      CashBalanceEvent cashBalanceEvent = (CashBalanceEvent) newBody;
      result.setReal(cashBalanceEvent.getReal());
    }
  }

  @Override
  public void onCompletion(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    BetBucket response = aggregatedResponses.get(correlationId);
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
