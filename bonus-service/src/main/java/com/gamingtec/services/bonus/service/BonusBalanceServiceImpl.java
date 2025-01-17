package com.gamingtec.services.bonus.service;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("bonusBalanceService")
@RequiredArgsConstructor
class BonusBalanceServiceImpl implements BonusBalanceService {

  @Override
  public BonusBalanceEvent getBonusBalance(BalanceRequestEvent event) {
    return BonusBalanceEvent.builder()
        .partyId(event.getPartyId())
        .releasedBonus(new BigDecimal(300))
        .playableBonus(new BigDecimal(400))
        .build();
  }
}

