package com.example.providerservice.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProviderBalanceReqDto {
    private Integer userId;
    private String sessionId;
}
