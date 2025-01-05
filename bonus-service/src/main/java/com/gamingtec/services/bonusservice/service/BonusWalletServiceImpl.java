package com.gamingtec.services.bonusservice.service;

import com.gamingtec.services.bonusservice.kafka.publisher.BonusBalancePublisher;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceReqEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
class BonusWalletServiceImpl implements BonusWalletService {
  private final BonusBalancePublisher bonusBalancePublisher;

  @Override
  public void getBalance(BonusBalanceReqEvent event) {
    bonusBalancePublisher.sendBonusBalanceEvent(new BonusBalanceEvent());
  }
}
