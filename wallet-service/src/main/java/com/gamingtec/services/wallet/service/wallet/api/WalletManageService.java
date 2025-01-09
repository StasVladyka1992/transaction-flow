package com.gamingtec.services.wallet.service.wallet.api;

import com.gamingtec.services.wallet.service.balance.model.BalanceRequest;

public interface WalletManageService {
  void getBalance(BalanceRequest req);
}
