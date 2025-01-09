package com.gamingtec.services.wallet.service.balance;

import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.wallet.service.balance.model.Balance;

public interface BalanceManageService {
  Balance sum(Balance cash, BonusBalanceEvent bonus);
}
