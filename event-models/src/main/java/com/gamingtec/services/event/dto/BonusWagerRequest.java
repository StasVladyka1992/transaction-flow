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
@EqualsAndHashCode
public class BonusWagerRequest {
  private int partyId;
  private int accountId;

  private int brandId;
  private int platformId;
  private String platformCode; // TODO if we have platformCode or id, why do we need second attribute? refactor

  private int gameInfoId;
  private String gameId; // TODO if we have gameInfoId, why do we need gameId? refactor
  private String gameType;

  private String platformGameTranId;
  private String platformTranId;

  private String currency;

  private BigDecimal real;
  private BigDecimal releasedBonus;
  private BigDecimal playableBonus;
}
