package com.gamingtec.services.provider.service;

import com.gamingtec.services.provider.route.dto.BalanceDto;
import com.gamingtec.services.provider.route.dto.BalanceReqDto;

public interface TestWalletService {
  BalanceDto getBalance(BalanceReqDto reqDto);
}
