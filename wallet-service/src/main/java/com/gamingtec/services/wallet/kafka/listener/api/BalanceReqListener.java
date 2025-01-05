package com.gamingtec.services.wallet.kafka.listener.api;

import com.gamingtec.services.event.dto.BalanceReqEvent;
import com.gamingtec.services.wallet.service.wallet.api.WalletService;
import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceReqListener {
  private final WalletService walletService;

  @KafkaListener(topics = "balance-request", groupId = "a")
  public void balanceRequest(BalanceReqEvent event) {
    log.info("BalanceReqEvent received: {}", event);
    walletService.balanceRequest(BalanceReq.builder()
        .partyId(event.getPartyId())
        .build());
  }
}

