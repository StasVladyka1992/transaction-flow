package com.gamingtec.services.provider.service.wallet;

import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceDto;
import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceReqDto;

public interface WalletService {
  void getBalance(BalanceReqDto req);

  void balance(BalanceDto balanceDto);
}
