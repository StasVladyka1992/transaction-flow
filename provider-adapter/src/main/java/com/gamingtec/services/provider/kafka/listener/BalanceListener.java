package com.gamingtec.services.provider.kafka.listener;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.provider.controller.dto.BalanceDto;
import com.gamingtec.services.provider.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceListener {
  private final WalletService walletService;

  @KafkaListener(topics = "balance", groupId = "a")
  public void balance(BalanceEvent event) {
    log.info("BonusBalanceEvent received: {}", event);
    walletService.balance(BalanceDto.builder()
        .partyId(event.getPartyId())
        .sessionId(event.getSessionId())
        .build());
  }
}
