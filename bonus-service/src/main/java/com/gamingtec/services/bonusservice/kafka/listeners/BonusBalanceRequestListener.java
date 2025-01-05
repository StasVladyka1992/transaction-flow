package com.gamingtec.services.bonusservice.kafka.listeners;

import com.gamingtec.services.bonusservice.service.BonusWalletService;
import com.gamingtec.services.event.dto.BonusBalanceReqEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BonusBalanceRequestListener {
  private final BonusWalletService bonusWalletService;

  //  @Transactional("kafkaTransactionManager")
  @KafkaListener(topics = {"bonus-balance-request"}, groupId = "a")
  public void bonusBalance(BonusBalanceReqEvent event) {
    log.info("BonusBalanceEvent received: {}", event);
    bonusWalletService.getBalance(event);
  }
}
