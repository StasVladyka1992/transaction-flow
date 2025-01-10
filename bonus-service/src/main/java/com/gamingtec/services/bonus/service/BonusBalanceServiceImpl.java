package com.gamingtec.services.bonus.service;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BonusBalanceEvent;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service("bonusBalanceService")
@RequiredArgsConstructor
class BonusBalanceServiceImpl implements BonusBalanceService {
  private final ProducerTemplate producerTemplate;

  @Override
  public void getBonusBalance(BalanceEvent event){
    BonusBalanceEvent bonusBalance = BonusBalanceEvent.builder()
        .correlationId(event.getCorrelationId())
        .balance(new BigDecimal("300"))
        .build();
    producerTemplate.sendBodyAndHeader("direct:balance", bonusBalance, "balanceType",  "bonus");
  }
}

