package com.gamingtec.services.event.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEvent {
  private int accountId;
  private int partyId;
  private BigDecimal real;
  private BigDecimal releasedBonus;
  private BigDecimal playableBonus;
  private long loyaltyPoints;
  private String currency;
}
