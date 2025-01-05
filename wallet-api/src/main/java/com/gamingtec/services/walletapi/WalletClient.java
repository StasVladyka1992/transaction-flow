package com.gamingtec.services.walletapi;

import com.gamingtec.services.walletapi.dto.BalanceReqDto;

public interface WalletClient {
  void balanceRequest(BalanceReqDto dto);
}
