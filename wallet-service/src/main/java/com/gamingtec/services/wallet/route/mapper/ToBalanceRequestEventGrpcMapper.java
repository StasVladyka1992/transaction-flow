package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class ToBalanceRequestEventGrpcMapper {
  public static BalanceRequestEvent toBalanceRequestEvent(WalletMessages.BalanceRequestGrpc requestEvent) {
    return BalanceRequestEvent.builder()
        .correlationId(String.valueOf(requestEvent.getPartyId()))
        .partyId(requestEvent.getPartyId())
        .build();
  }
}
