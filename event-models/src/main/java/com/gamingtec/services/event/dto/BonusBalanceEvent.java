package com.gamingtec.services.event.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BonusBalanceEvent extends AbstractBalanceEvent {
  private Integer partyId;
  private BigDecimal releasedBonus;
  private BigDecimal playableBonus;
}
