package com.gamingtec.services.bonus.service;

import com.gamingtec.services.event.dto.BalanceEvent;

public interface BonusBalanceService {
  void getBonusBalance(BalanceEvent event);
}
