package com.gamingtec.services.wallet.kafka.publisher.internal;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.wallet.kafka.publisher.api.BalancePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class BalancePublisherImpl implements BalancePublisher {
  private final KafkaTemplate<String, BalanceEvent> balanceTemplate;

  @Override
  public void sendBalanceEvent(BalanceEvent balanceEvent) {
    try {
      balanceTemplate.send("balance", balanceEvent);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
