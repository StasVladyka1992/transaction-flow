package com.gamingtec.services.bonusservice.service;


import com.gamingtec.services.event.dto.BalanceEvent;

public interface BonusWalletService {
  void getBalance(BalanceEvent event);
}
