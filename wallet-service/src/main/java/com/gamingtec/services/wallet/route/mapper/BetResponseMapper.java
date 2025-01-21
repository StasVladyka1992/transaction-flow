package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BetResponseEvent;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BetResponseMapper {
  private final BalanceMapper balanceMapper;

  public WalletMessages.BetResponseGrpc toGrpc(BetResponseEvent betResponse) {
    return WalletMessages.BetResponseGrpc.newBuilder()
        .setTransactionId(betResponse.getTransactionId())
        .setBalance(balanceMapper.toBalanceGrpc(betResponse.getBalance()))
        .build();
  }
}
