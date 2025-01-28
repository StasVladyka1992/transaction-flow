package com.gamingtec.services.bonus.service;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
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

  @Override
  public BonusBalanceEvent bet(BetRequestEvent event) {
    BonusBalanceEvent bonusBalance = BonusBalanceEvent.builder()
        .partyId(event.getPartyId())
        .releasedBonus(new BigDecimal(300).subtract(event.getReleasedBonus()))
        .playableBonus(new BigDecimal(400).subtract(event.getPlayableBonus()))
        .build();

    wagerBet(event);
    return bonusBalance;
  }

  @Override
  public void wagerBet(BetRequestEvent event){ //TODO think about place where to initiate transaction.
    log.info("Wagering request: {}", event);
  }
}

