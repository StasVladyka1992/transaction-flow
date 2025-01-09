package com.gamingtec.services.wallet.service.balance.route;

import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.wallet.service.balance.BalanceManageService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceRouteConfig {
  private final BalanceManageService balanceManageService;

  @Bean
  public KStream<Integer, BonusBalanceEvent> stream(StreamsBuilder builder) {
//    JsonSerde<Balance> balanceSerde = new JsonSerde<>(Balance.class);
    JsonSerde<BonusBalanceEvent> bonusBalanceEventSerde = new JsonSerde<>(BonusBalanceEvent.class);
    KStream<Integer, BonusBalanceEvent> stream = builder
        .stream("bonus-balance", Consumed.with(Serdes.Integer(), bonusBalanceEventSerde));
//        .flatMapValues(bonusBalanceEvent -> {
//         return BonusBalanceEvent.builder()
//              .build();
//        })
//    stream.join(
//            builder.stream("bonus-balance"),
//            balanceManageService::sum,
//            JoinWindows.of(Duration.ofSeconds(10)),
//            StreamJoined.with(Serdes.Long(), balanceSerde, balanceSerde))
//        .peek((k, o) -> log.info("Output: {}", o))
//        .to("player-balance");

    return stream;
  }

}
