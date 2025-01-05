package com.gamingtec.services.bonusservice.kafka.publisher;


import com.gamingtec.services.event.dto.BonusBalanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BonusBalancePublisher {
  private final KafkaTemplate<String, BonusBalanceEvent> bonusBalanceTemplate;

  public void sendBonusBalanceEvent(BonusBalanceEvent event) {
    bonusBalanceTemplate.send("bonus-balance", event);
  }
}
