package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class ToBalanceGrpcEventMapper {
  public static WalletMessages.BalanceGrpc toBalanceGrpc(BalanceEvent balanceEvent) {
    return WalletMessages.BalanceGrpc.newBuilder()
        .setBalance(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFrom(balanceEvent.getCash().unscaledValue().toByteArray()))
            .setPrecision(balanceEvent.getCash().precision())
            .setScale(balanceEvent.getCash().scale())
            .build())
        .build();
  }
}
