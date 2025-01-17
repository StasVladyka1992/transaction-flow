package com.gamingtec.services.provider.route.mapper;


import com.gamingtec.services.provider.route.dto.BalanceReqDto;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class BalanceRequestMapper {
  public WalletMessages.BalanceRequestGrpc toGrpc(BalanceReqDto dto) {
    return WalletMessages.BalanceRequestGrpc.newBuilder()
        .setPartyId(dto.getPartyId())
        .setBrandId(dto.getBrandId())
        .setGameId(dto.getGameId())
        .setPlatformCode(dto.getPlatformCode())
        .setPlayerCurrency(dto.getPlayerCurrency())
        .setNumDecimalParts(dto.getNumDecimalParts())
        .build();
  }
}
