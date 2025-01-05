package com.gamingtec.services.bonusservice.service;


import com.gamingtec.services.event.dto.BonusBalanceReqEvent;

public interface BonusWalletService {
  void getBalance(BonusBalanceReqEvent event);
}
