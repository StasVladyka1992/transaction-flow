package com.gamingtec.services.bonusservice.service;


import com.gamingtec.services.event.dto.BalanceRequestEvent;

public interface BonusWalletService {
  void getBalance(BalanceRequestEvent event);
}
