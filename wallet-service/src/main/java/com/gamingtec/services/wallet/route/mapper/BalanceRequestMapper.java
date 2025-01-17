package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class BalanceRequestMapper {
  public BalanceRequestEvent toBalanceRequestEvent(WalletMessages.BalanceRequestGrpc requestEvent) {
    return  BalanceRequestEvent.builder()
        .brandId(requestEvent.getBrandId())
        .partyId(requestEvent.getPartyId())
        .gameId(requestEvent.getGameId())
        .platformCode(requestEvent.getPlatformCode())
        .playerCurrency(requestEvent.getPlayerCurrency())
        .numDecimalPart(requestEvent.getNumDecimalParts())
        .build();
  }
}
