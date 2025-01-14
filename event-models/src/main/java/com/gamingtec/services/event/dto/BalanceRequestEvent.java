package com.gamingtec.services.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BalanceRequestEvent {
  private Integer partyId;

  public BalanceRequestEvent(Integer partyId) {
    this.partyId = partyId;
  }
}
