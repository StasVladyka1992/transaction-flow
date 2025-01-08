package com.gamingtec.services.provider.service.wallet;

import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceDto;
import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class WalletServiceImpl implements WalletService {
//  private final WalletClient walletClient;

  @Override
  public void getBalance(BalanceReqDto req) {
    var grpcReq = com.gamingtec.services.walletapi.dto.BalanceReqDto
        .builder()
        .partyId(req.getPartyId())
        .sessionId(req.getSessionId())
        .build();
//    walletClient.balanceRequest(grpcReq);
  }

  @Override
  public void balance(BalanceDto balanceDto){
  }
}
