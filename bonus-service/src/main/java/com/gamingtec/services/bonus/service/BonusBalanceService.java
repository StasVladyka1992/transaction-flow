package com.gamingtec.services.bonus.service;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;

public interface BonusBalanceService {
  BonusBalanceEvent getBonusBalance(BalanceRequestEvent event);
  BonusBalanceEvent bet(BetRequestEvent event);
}
