package com.gamingtec.services.wallet.service.cash.internal;

import com.gamingtec.services.wallet.service.cash.api.CashService;
import com.gamingtec.services.wallet.service.wallet.api.model.Balance;
import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
class CashServiceImpl implements CashService {

  @Override
  public Balance getBalance(BalanceReq req){
    return new Balance();
  }
}
