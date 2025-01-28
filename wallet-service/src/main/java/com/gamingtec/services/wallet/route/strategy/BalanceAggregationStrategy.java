package com.gamingtec.services.wallet.route.strategy;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;
import static com.gamingtec.services.wallet.service.cache.config.CachNames.BALANCE_AGGREGATION;

import com.gamingtec.services.event.dto.AbstractBalanceEvent;
import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;
import com.gamingtec.services.wallet.repository.redis.entity.AggregatedBalanceEntity;
import com.gamingtec.services.wallet.repository.redis.entity.Balance;
import com.gamingtec.services.wallet.service.cache.CacheService;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceAggregationStrategy implements AggregationStrategy {
  private final CacheService cacheService;

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    String correlationId = newExchange.getIn().getHeader(CORRELATION_ID, String.class);
    Object body = newExchange.getIn().getBody();
    Exchange exchangeToReturn = oldExchange == null ? newExchange : oldExchange;
    handleBody(correlationId, body, exchangeToReturn);
    return exchangeToReturn;
  }

  private void handleBody(String correlationId, Object body, Exchange exchange) {
    AbstractBalanceEvent event = (AbstractBalanceEvent) body;
    if (event != null) {
      String eventType = getEventType(event);
      Balance balance = ofEvent(eventType, event);
      log.info("Received {} balance response for correlationId: {}, {}", eventType, correlationId, balance);

      var entity = Optional
          .ofNullable(cacheService.getValue(BALANCE_AGGREGATION, correlationId, AggregatedBalanceEntity.class))
          .orElse(AggregatedBalanceEntity.builder()
              .balances(new HashMap<>())
              .build());

      if (entity.getBalances().isEmpty()) {
        entity.getBalances().put(eventType, balance);
        cacheService.putValue(BALANCE_AGGREGATION, correlationId, entity);
      } else if (entity.getBalances().size() == 1 && !entity.getBalances().containsKey(eventType)) {
        entity.getBalances().put(eventType, balance); //TODO there is no need to put. will be enough to use as is.
        log.info("Aggregation will be completed, correlationId: {}, {}", correlationId, entity);
        BalanceEvent total = BalanceEvent.builder()
            .partyId(1)
            .accountId(1)
            .currency("USD")
            .real(entity.getBalances().get("CASH").getReal())
            .releasedBonus(entity.getBalances().get("BONUS").getReleasedBonus())
            .playableBonus(entity.getBalances().get("BONUS").getPlayableBonus())
            .build();
        exchange.getIn().setBody(total);
        exchange.setProperty(Exchange.AGGREGATION_COMPLETE_ALL_GROUPS_INCLUSIVE, true);
      }
    }
  }

  @Override
  public void onCompletion(Exchange exchange) {
    cacheService.evictValue(BALANCE_AGGREGATION, exchange.getIn().getHeader(CORRELATION_ID, String.class));
  }

  @Override
  public void timeout(Exchange exchange, int index, int total, long timeout) {
    cacheService.evictValue(BALANCE_AGGREGATION, exchange.getIn().getHeader(CORRELATION_ID, String.class));
  }

  private String getEventType(AbstractBalanceEvent event) {
    if (event instanceof BonusBalanceEvent) {
      return "BONUS";
    } else {
      return "CASH";
    }
  }

  private Balance ofEvent(String type, AbstractBalanceEvent abstractEvent) {
    return switch (type) {
      case "BONUS" -> ofBonusBalance(abstractEvent);
      default -> ofCashBalance(abstractEvent);
    };
  }

  private Balance ofCashBalance(AbstractBalanceEvent abstractEvent) {
    CashBalanceEvent event = (CashBalanceEvent) abstractEvent;
    return Balance.builder()
        .partyId(event.getPartyId())
        .real(event.getReal())
        .build();
  }

  private Balance ofBonusBalance(AbstractBalanceEvent abstractEvent) {
    BonusBalanceEvent event = (BonusBalanceEvent) abstractEvent;
    return Balance.builder()
        .partyId(event.getPartyId())
        .releasedBonus(event.getReleasedBonus())
        .playableBonus(event.getPlayableBonus())
        .build();
  }
}
