package com.gamingtec.services.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BalanceType {
  CASH,
  BONUS,
  LOYALTY
}
