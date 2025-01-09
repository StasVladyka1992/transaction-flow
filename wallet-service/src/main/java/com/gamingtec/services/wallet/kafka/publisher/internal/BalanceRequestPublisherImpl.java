package com.gamingtec.services.wallet.kafka.publisher.internal;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.wallet.kafka.publisher.api.BalanceRequestPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class BalanceRequestPublisherImpl implements BalanceRequestPublisher {
  private final KafkaTemplate<Integer, BalanceRequestEvent> balanceTemplate;

  @Override
  public void sendBalanceRequestEvent(BalanceRequestEvent event) {
    try {
      balanceTemplate.send("balance-request", event.getPartyId(), event);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
