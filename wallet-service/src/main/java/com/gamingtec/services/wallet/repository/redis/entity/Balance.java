package com.gamingtec.services.wallet.repository.redis.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
  private int partyId;
  private int accountId;
  private BigDecimal real;
  private BigDecimal releasedBonus;
  private BigDecimal playableBonus;
  private long loyaltyPoints;
  private String currency;
}
