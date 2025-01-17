package com.gamingtec.services.provider.route.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalanceReqDto {
    private int partyId;
    private int brandId;
    private String gameId;
    private String platformCode;
    private String playerCurrency;
    private Integer numDecimalParts;
}
