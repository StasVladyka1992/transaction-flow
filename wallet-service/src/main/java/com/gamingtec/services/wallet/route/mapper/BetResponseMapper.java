package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BetResponseEvent;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BetResponseMapper {
  private final BalanceMapper balanceMapper;

  public WalletMessages.BetResponseGrpc toGrpc(BetResponseEvent betResponse, @Header("correlationId") String correlationId) {
    log.info("BetResponseMapper toGrpc, correlationId: {},  {}", correlationId, betResponse);
    return WalletMessages.BetResponseGrpc.newBuilder()
        .setTransactionId(betResponse.getTransactionId())
        .setBalance(balanceMapper.toBalanceGrpc(betResponse.getBalance()))
        .build();
  }
}
