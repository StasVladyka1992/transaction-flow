package com.gamingtec.services.cash.service;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;

public interface CashBalanceService {
  CashBalanceEvent getCashBalance(BalanceRequestEvent event);

  CashBalanceEvent bet(BetRequestEvent event);
}
