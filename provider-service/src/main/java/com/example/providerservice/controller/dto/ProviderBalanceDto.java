package com.example.providerservice.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProviderBalanceDto {
    private BigDecimal balance;
    private Integer partyId;
}
