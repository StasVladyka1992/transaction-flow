package com.gamingtec.services.provider.route.mapper;

import com.gamingtec.services.provider.route.dto.BetRequestDto;
import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class BetRequestMapper {
  public com.gamingtec.wallet.WalletMessages.BetRequestGrpc toGrpc(BetRequestDto dto){
    return WalletMessages.BetRequestGrpc.newBuilder()
        .setPartyId(dto.getPartyId())
        .setAccountId(dto.getAccountId())
        .setPlatformId(dto.getPlatformId())
        .setPlatformCode(dto.getPlatformCode())
        .setGameId(dto.getGameId())
        .setGameInfoId(dto.getGameInfoId())
        .setSportbook(dto.isSportbook())
        .setPlayerCurrency(dto.getCurrency())
        .setPlatformGameTranId(dto.getPlatformGameTranId())
        .setPlatformTranId(dto.getPlatformTranId())
        .setAmount(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFrom(dto.getAmount().unscaledValue().toByteArray()))
            .setScale(dto.getAmount().scale())
            .setPrecision(dto.getAmount().precision())
            .build())

        .build();
  }
}
