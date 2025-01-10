package com.gamingtec.services.provider.route.mapper;

import com.gamingtec.services.provider.route.dto.BalanceDto;
import com.gamingtec.wallet.WalletMessages;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.springframework.stereotype.Component;

@Component
public class BalanceGrpcMapper {
  public static BalanceDto toDto(WalletMessages.BalanceGrpc dto) {
    MathContext mc = new MathContext(dto.getBalance().getPrecision());
    BigDecimal balance = new BigDecimal(new BigInteger(dto.getBalance().getValue().toByteArray()),
        dto.getBalance().getScale(),
        mc);
    return BalanceDto.builder()
        .partyId(1)
        .balance(balance)
        .build();
  }
}
