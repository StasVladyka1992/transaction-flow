package com.gamingtec.services.wallet.service.cash.api;

import com.gamingtec.services.wallet.service.wallet.api.model.Balance;
import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;

public interface CashService {
  Balance getBalance(BalanceReq req);
}
