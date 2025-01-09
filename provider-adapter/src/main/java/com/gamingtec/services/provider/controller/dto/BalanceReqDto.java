package com.gamingtec.services.provider.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalanceReqDto {
    private Integer partyId;
    private String sessionId;
}
