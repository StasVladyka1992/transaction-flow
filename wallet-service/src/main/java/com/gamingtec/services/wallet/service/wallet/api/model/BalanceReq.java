package com.gamingtec.services.wallet.service.wallet.api.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class BalanceReq {
  private Integer partyId;
  private String currency;
  private String gameProvider;
  private String gameId;
}
