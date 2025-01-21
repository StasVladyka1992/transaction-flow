package com.gamingtec.services.wallet.service;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletService {
  private final ProducerTemplate producerTemplate;

  public BetRequestEvent initGetBalance(BetRequestEvent betRequestEvent, Exchange exchange) {
    String correlationId = exchange.getIn().getHeader(CORRELATION_ID, String.class);
    BalanceRequestEvent balanceRequestEvent = BalanceRequestEvent.builder()
        .partyId(betRequestEvent.getPartyId())
        .playerCurrency(betRequestEvent.getCurrency())
        .brandId(betRequestEvent.getBrandId())
        .platformCode(betRequestEvent.getPlatformCode())
        .gameId(betRequestEvent.getGameId())
        .build();

    log.info("Bet request to kafka will be sent, correlationId: {}", correlationId);

    producerTemplate.sendBodyAndHeader("kafka:balanceRequest", balanceRequestEvent, CORRELATION_ID, correlationId);
    return betRequestEvent;
  }

  public void bet(BetBucket bucket) {

  }
}
