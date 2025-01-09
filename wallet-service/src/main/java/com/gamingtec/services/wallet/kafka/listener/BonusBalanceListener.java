package com.gamingtec.services.wallet.kafka.listener;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class BonusBalanceListener {
//  private final BonusWalletService bonusWalletService;
//
//  @KafkaListener(id="balance-request",  topics = "balance-request", groupId = "bonus")
//  public void onEvent(BalanceRequestEvent event) {
//    log.info("BalanceRequestEvent received: {}", event);
//    bonusWalletService.getBalance(event);
//  }
//}

