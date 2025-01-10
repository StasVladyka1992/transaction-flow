package com.gamingtec.services.event.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class Event {
  private String correlationId;
}
