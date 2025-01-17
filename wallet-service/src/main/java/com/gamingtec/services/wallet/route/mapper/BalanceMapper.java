package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.ByteString;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {
  public WalletMessages.BalanceGrpc toBalanceGrpc(BalanceEvent balanceEvent) {
    BigDecimal real = balanceEvent.getReal();
    BigDecimal releasedBonus = balanceEvent.getReleasedBonus();
    BigDecimal playableBonus = balanceEvent.getPlayableBonus();

    return WalletMessages.BalanceGrpc.newBuilder()
        .setPartyId(balanceEvent.getPartyId())
        .setAccountId(balanceEvent.getAccountId())
        .setReal(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFrom(real.unscaledValue().toByteArray()))
            .setPrecision(real.precision())
            .setScale(real.scale())
            .build())
        .setReleasedBonus(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFrom(releasedBonus.unscaledValue().toByteArray()))
            .setPrecision(releasedBonus.precision())
            .setScale(releasedBonus.scale())
            .build())
        .setPlayableBonus(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFrom(playableBonus.unscaledValue().toByteArray()))
            .setPrecision(playableBonus.precision())
            .setScale(playableBonus.scale())
            .build())
        .build();
  }
}
