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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BetRequestDto {
  private int partyId;
  private BigDecimal amount;
  private String currency;
  private int platformId;
  private String platformCode;
  private boolean sportbook;
  private int gameInfoId;
  private String gameId;
  private String platformGameTranId;
  private String platformTranId;
}
