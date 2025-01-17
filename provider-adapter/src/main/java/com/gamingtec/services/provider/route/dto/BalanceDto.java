package com.gamingtec.services.provider.route.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {
  private int partyId;
  private int accountId;
  private BigDecimal real;
  private BigDecimal releasedBonus;
  private BigDecimal playableBonus;
}
