package com.gamingtec.services.wallet.service.wallet.internal;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.wallet.kafka.publisher.api.BalanceRequestPublisher;
import com.gamingtec.services.wallet.service.balance.model.BalanceRequest;
import com.gamingtec.services.wallet.service.wallet.api.WalletManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class WalletManageServiceImpl implements WalletManageService {
  private final BalanceRequestPublisher balanceRequestPublisher;

  @Override
  public void getBalance(BalanceRequest req) {
    var event = BalanceRequestEvent.builder()
        .id(req.getId())
        .partyId(req.getPartyId())
        .build();
    balanceRequestPublisher.sendBalanceRequestEvent(event);
  }
}
