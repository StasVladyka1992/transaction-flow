package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class BalanceRequestEventMapper {
  public static BalanceRequestEvent toBalanceRequestEvent(WalletMessages.BalanceRequestGrpc requestEvent) {
    return new BalanceRequestEvent(requestEvent.getPartyId());
  }
}
