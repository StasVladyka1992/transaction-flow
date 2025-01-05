package com.gamingtec.services.wallet.kafka.publisher.internal;

import com.gamingtec.services.event.dto.BonusBalanceReqEvent;
import com.gamingtec.services.wallet.kafka.publisher.api.BonusPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class BonusPublisherImpl implements BonusPublisher {
  private final KafkaTemplate<String, BonusBalanceReqEvent> bonusBalanceTemplate;
  private final KafkaTemplate<String, String> bonusBetTemplate;

  @Override
  public void sendBonusBalanceReqEvent(BonusBalanceReqEvent event){
    try {
      bonusBalanceTemplate.send("bonus-balance-request", event);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
