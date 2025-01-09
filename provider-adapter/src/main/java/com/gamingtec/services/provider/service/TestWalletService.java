package com.gamingtec.services.provider.service;

import com.gamingtec.services.provider.controller.dto.BalanceDto;
import com.gamingtec.services.provider.controller.dto.BalanceReqDto;

public interface TestWalletService {
  BalanceDto getBalance(BalanceReqDto reqDto);
}
