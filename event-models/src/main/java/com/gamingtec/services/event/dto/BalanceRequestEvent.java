package com.gamingtec.services.event.dto;

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
public class BalanceRequestEvent {
  private int partyId;
  private int brandId;
  private String gameId;
  private String platformCode;
  private String playerCurrency;
  private Integer numDecimalPart;
}
