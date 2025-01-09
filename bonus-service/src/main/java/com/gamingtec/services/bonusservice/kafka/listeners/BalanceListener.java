package com.gamingtec.services.bonusservice.kafka.listeners;

import com.gamingtec.services.bonusservice.service.BonusWalletService;
import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceListener {
  private final BonusWalletService bonusWalletService;

  @KafkaListener(id="balance-request",  topics = "balance-request", groupId = "bonus")
  public void onEvent(BalanceRequestEvent event) {
    log.info("BalanceRequestEvent received: {}", event);
    bonusWalletService.getBalance(event);
  }
}
