package com.gamingtec.services.wallet.service.wallet.internal;

import com.gamingtec.services.event.dto.BonusBalanceReqEvent;
import com.gamingtec.services.wallet.kafka.publisher.api.BalancePublisher;
import com.gamingtec.services.wallet.kafka.publisher.api.BonusPublisher;
import com.gamingtec.services.wallet.service.cash.api.CashService;
import com.gamingtec.services.wallet.service.wallet.api.WalletService;
import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import com.gamingtec.services.wallet.service.wallet.api.model.BonusBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class WalletServiceImpl implements WalletService {
  private final BonusPublisher bonusPublisher;
  private final BalancePublisher balancePublisher;
  private final CashService cashService;

  @Override
//  @Transactional("kafkaTransactionManager")
  public void balanceRequest(BalanceReq req) {
    bonusPublisher.sendBonusBalanceReqEvent(BonusBalanceReqEvent.builder()
        .partyId(req.getPartyId())
        .build());
    //cashService.getBalance(req);
    //TODO write to redis + lock
  }

  @Override
  public void bonusBalance(BonusBalance bonusBalance) {
    //TODO write to redis
  }
}
