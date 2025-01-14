package com.gamingtec.services.event.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CashBalanceEvent.class, name = "cash"),
    @JsonSubTypes.Type(value = BonusBalanceEvent.class, name = "bonus"),
    @JsonSubTypes.Type(value = LoyaltyBalanceEvent.class, name = "loyalty")
})
public class AbstractBalanceEvent {
}
