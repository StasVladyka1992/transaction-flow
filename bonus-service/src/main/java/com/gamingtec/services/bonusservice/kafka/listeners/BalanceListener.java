package com.gamingtec.services.bonusservice.kafka.listeners;

import com.gamingtec.services.bonusservice.service.BonusWalletService;
import com.gamingtec.services.event.dto.BalanceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceListener {
  private final BonusWalletService bonusWalletService;

  @KafkaListener(id="balance",  topics = "balance", groupId = "bonus")
  public void onEvent(BalanceEvent event) {
    log.info("BonusBalanceEvent received: {}", event);
    bonusWalletService.getBalance(event);
  }
}
