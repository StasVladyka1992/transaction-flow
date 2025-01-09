package com.gamingtec.services.wallet.service.balance;

import com.gamingtec.services.event.dto.BonusBalanceEvent;
import com.gamingtec.services.wallet.service.balance.model.Balance;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BalanceManageServiceImpl implements BalanceManageService {
  @Override
  public Balance sum(Balance cash, BonusBalanceEvent bonus) {
    return new Balance(1L, 1, BigDecimal.TEN);
  }
}
