package com.gamingtec.services.provider.route.mapper;

import com.gamingtec.services.provider.route.dto.BalanceDto;
import com.gamingtec.wallet.WalletMessages;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {
  public BalanceDto toDto(WalletMessages.BalanceGrpc dto) {
    WalletMessages.DecimalValue real = dto.getReal();
    WalletMessages.DecimalValue releasedBonus = dto.getReleasedBonus();
    WalletMessages.DecimalValue playableBonus = dto.getPlayableBonus();

    MathContext realMc = new MathContext(real.getPrecision());
    BigDecimal realBalance = new BigDecimal(new BigInteger(real.getValue().toByteArray()), real.getScale(), realMc);

    MathContext releasedMc = new MathContext(releasedBonus.getPrecision());
    BigDecimal releasedBalance = new BigDecimal(new BigInteger(releasedBonus.getValue().toByteArray()), releasedBonus.getScale(), releasedMc);

    MathContext playableMc = new MathContext(playableBonus.getPrecision());
    BigDecimal playableBalance = new BigDecimal(new BigInteger(playableBonus.getValue().toByteArray()), playableBonus.getScale(), playableMc);

    return BalanceDto.builder()
        .partyId(dto.getPartyId())
        .accountId(dto.getAccountId())
        .real(realBalance)
        .releasedBonus(releasedBalance)
        .playableBonus(playableBalance)
        .build();
  }
}
