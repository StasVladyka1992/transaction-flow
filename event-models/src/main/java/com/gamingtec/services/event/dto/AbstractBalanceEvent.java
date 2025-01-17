package com.gamingtec.services.event.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = false)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CashBalanceEvent.class, name = "CASH"),
    @JsonSubTypes.Type(value = BonusBalanceEvent.class, name = "BONUS"),
    @JsonSubTypes.Type(value = LoyaltyBalanceEvent.class, name = "LOYALTY")
})
public abstract class AbstractBalanceEvent {
}
