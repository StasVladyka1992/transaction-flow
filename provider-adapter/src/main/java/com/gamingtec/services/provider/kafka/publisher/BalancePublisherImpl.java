package com.gamingtec.services.provider.kafka.publisher;

import com.gamingtec.services.event.dto.BalanceReqEvent;
import com.gamingtec.services.provider.controller.dto.BalanceReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class BalancePublisherImpl implements BalancePublisher {
  private static String BALANCE_REQUEST_TOPIC = "balance-request";

  private final KafkaTemplate<String, BalanceReqEvent> balanceRequestTemplate;

  @Override
  public void sendBalanceReqEvent(BalanceReqDto dto) {
    try {
      log.info("Send event to topic: {}", BALANCE_REQUEST_TOPIC);
      BalanceReqEvent event = BalanceReqEvent.builder()
          .partyId(dto.getPartyId())
          .sessionId(dto.getSessionId())
          .build();
      balanceRequestTemplate.send(BALANCE_REQUEST_TOPIC, event);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
