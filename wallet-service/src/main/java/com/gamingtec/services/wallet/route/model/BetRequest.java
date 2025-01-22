package com.gamingtec.services.wallet.route.model;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BetRequest {
  private int partyId;
  private int accountId;

  private BigDecimal amount;
  private String currency;

  private int brandId;
  private int platformId;
  private String platformCode; // TODO if we have platformCode or id, why do we need second attribute? refactor

  private boolean sportbook;

  private int gameInfoId;
  private String gameId; // TODO if we have gameInfoId, why do we need gameId? refactor

  private String platformGameTranId;
  private String platformTranId;
}
