package com.gamingtec.services.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoyaltyBalanceEvent extends AbstractBalanceEvent {
    private Integer partyId;
    private long loyaltyBalance;
}
