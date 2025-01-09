package com.gamingtec.services.wallet.service.balance.model;


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
public class BalanceRequest {
  private Long id;
  private String gameId;
  private Integer partyId;
  private String currency;
  private String gameProvider;
}
