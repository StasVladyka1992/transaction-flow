package com.gamingtec.services.wallet.service.wallet.route;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.wallet.service.balance.BalanceManageService;
import com.gamingtec.services.wallet.service.balance.model.Balance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.checkerframework.checker.units.qual.K;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceRouteConfig {
  private final BalanceManageService balanceManageService;

  @Bean
  public KStream<Integer, BalanceEvent> stream(StreamsBuilder builder) {
//    JsonSerde<Balance> balanceSerde = new JsonSerde<>(Balance.class);
    JsonSerde<BonusBalanceEvent> bonusBalanceEventSerde = new JsonSerde<>(BonusBalanceEvent.class);
    KStream<Integer, BalanceEvent> balanceStream = builder
        .stream("bonus-balance", Consumed.with(Serdes.Integer(), bonusBalanceEventSerde))
        .mapValues(bonusBalanceEvent -> {
          Balance balance = balanceManageService.sum(new Balance(), bonusBalanceEvent);
          return BalanceEvent.builder()
              .id(balance.getId())
              .partyId(balance.getPartyId())
              .bonusBalance(balance.getBalance())
              .build();
        })
        .peek((k, o) -> log.info("Output: {}", o));
    balanceStream.to("balance");
    return balanceStream;
  }
}
