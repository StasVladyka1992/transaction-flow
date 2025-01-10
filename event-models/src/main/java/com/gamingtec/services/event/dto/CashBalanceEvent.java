package com.gamingtec.services.event.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class CashBalanceEvent extends Event {
  private BigDecimal balance;
}
