package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.wallet.WalletMessages;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.springframework.stereotype.Component;

@Component
public class BetRequestMapper {
  public BetRequestEvent toBetRequestEvent(WalletMessages.BetRequestGrpc req) {
    WalletMessages.DecimalValue decimalAmount = req.getAmount();
    MathContext mc = new MathContext(decimalAmount.getPrecision());
    BigDecimal amount = new BigDecimal(
        new BigInteger(decimalAmount.getValue().toByteArray()),
        decimalAmount.getScale(),
        mc);

    return BetRequestEvent.builder()
        .partyId(req.getPartyId())
        .amount(amount)
        .currency(req.getPlayerCurrency())
        .platformId(req.getPlatformId())
        .platformCode(req.getPlatformCode())
        .sportbook(req.getSportbook())
        .gameInfoId(req.getGameInfoId())
        .gameId(req.getGameId())
        .platformGameTranId(req.getPlatformGameTranId())
        .platformTranId(req.getPlatformTranId())
        .build();
  }
}
