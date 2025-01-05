package com.gamingtec.services.wallet.service.wallet.api;

import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import com.gamingtec.services.wallet.service.wallet.api.model.BonusBalance;

public interface WalletService {
  void balanceRequest(BalanceReq req);
  void bonusBalance(BonusBalance bonusBalance);
}
