package com.gamingtec.services.event.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class BalanceRequestEvent extends Event {
  private Integer partyId;
}
