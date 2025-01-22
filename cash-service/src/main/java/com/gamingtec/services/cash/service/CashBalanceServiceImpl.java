package com.gamingtec.services.cash.service;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.event.dto.CashBalanceEvent;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CashBalanceServiceImpl implements CashBalanceService {

  @Override
  public CashBalanceEvent getCashBalance(BalanceRequestEvent event) {
    return new CashBalanceEvent(event.getPartyId(), new BigDecimal("300"));
  }

  @Override
  public CashBalanceEvent bet(BetRequestEvent event) {
    return CashBalanceEvent.builder()
        .partyId(event.getPartyId())
        .real(new BigDecimal(300))
        .build();
  }
}

