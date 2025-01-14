package com.gamingtec.services.event.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEvent {
  private String correlationId;
  private Integer partyId;
  private BigDecimal bonus;
  private BigDecimal cash;
  private long loyaltyPoints;
}
