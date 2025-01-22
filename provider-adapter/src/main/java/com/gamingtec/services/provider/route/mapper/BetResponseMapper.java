package com.gamingtec.services.provider.route.mapper;

import com.gamingtec.services.provider.route.dto.BetResponseDto;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BetResponseMapper {
  private final BalanceMapper balanceMapper;

  public BetResponseDto toDto(WalletMessages.BetResponseGrpc in){

    return BetResponseDto.builder()
        .transactionId(in.getTransactionId())
        .balance(balanceMapper.toDto(in.getBalance()))
        .build();
  }
}
