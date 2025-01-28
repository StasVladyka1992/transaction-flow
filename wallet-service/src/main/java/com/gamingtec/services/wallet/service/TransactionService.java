package com.gamingtec.services.wallet.service;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BetResponseEvent;

public interface TransactionService {
  BetResponseEvent createBetTransaction(BalanceEvent balanceEvent);
}
