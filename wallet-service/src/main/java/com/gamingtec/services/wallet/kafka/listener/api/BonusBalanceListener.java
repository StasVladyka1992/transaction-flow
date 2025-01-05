package com.gamingtec.services.wallet.kafka.listener.api;

import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.wallet.service.wallet.api.WalletService;
import com.gamingtec.services.wallet.service.wallet.api.model.BonusBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BonusBalanceListener {
  private final WalletService walletService;

//  @Transactional("kafkaTransactionManager")
  @KafkaListener(topics = "bonus-balance", groupId = "a")
  public void bonusBalance(BonusBalanceEvent event) {
    log.info("BonusBalanceEvent received: {}", event);
    walletService.bonusBalance(new BonusBalance());
  }
}
