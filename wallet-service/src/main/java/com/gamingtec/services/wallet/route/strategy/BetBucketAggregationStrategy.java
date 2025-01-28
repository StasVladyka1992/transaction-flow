package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import com.gamingtec.services.wallet.route.model.BetRequest;
import java.math.BigDecimal;
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
    BetBucket result = handleBody(correlationId, newBody, oldBody);
    oldExchange.getIn().setBody(result);
    return oldExchange;
  }

  private BetBucket handleBody(String correlationId, Object newBody, Object oldBody) {
    BetBucket result = aggregatedResponses.computeIfAbsent(correlationId, k -> new BetBucket());
    if (result.getPartyId() == 0) {
      BetRequest betRequestEvent = (BetRequest) oldBody;
      result = BetBucket.builder()
          .partyId(betRequestEvent.getPartyId())
          .accountId(betRequestEvent.getAccountId())
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
          .real(BigDecimal.ZERO)
          .releasedBonus(BigDecimal.ZERO)
          .playableBonus(BigDecimal.ZERO)
          .build();
    }

    BalanceEvent balance = (BalanceEvent) newBody;
    result.setReal(balance.getReal().add(result.getReal()));
    result.setReleasedBonus(balance.getReleasedBonus().add(result.getReleasedBonus()));
    result.setPlayableBonus(balance.getPlayableBonus().add(result.getPlayableBonus()));
    return result;
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
