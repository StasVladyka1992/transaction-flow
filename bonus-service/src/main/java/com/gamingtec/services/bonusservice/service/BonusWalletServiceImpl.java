package com.gamingtec.services.bonusservice.service;

import com.gamingtec.services.bonusservice.kafka.publisher.BonusBalancePublisher;
import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.event.dto.EventStatus;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
class BonusWalletServiceImpl implements BonusWalletService {
  private final BonusBalancePublisher bonusBalancePublisher;

  @Override
  public void getBalance(BalanceRequestEvent event) {
    var bonusBalanceEvent = BonusBalanceEvent.builder()
        .id(2L)
        .partyId(event.getPartyId())
        .status(EventStatus.NEW)
        .amount(BigDecimal.TEN)
        .build();

    bonusBalancePublisher.sendBonusBalanceEvent(bonusBalanceEvent);
  }
}
