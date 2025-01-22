package com.gamingtec.services.wallet.service;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import com.gamingtec.services.wallet.route.model.BetRequest;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletService {
  private final ObjectMapper objectMapper;
  private final ProducerTemplate producerTemplate;

  public BetRequest initGetBalance(BetRequest betRequest, Exchange exchange) {
    String correlationId = exchange.getIn().getHeader(CORRELATION_ID, String.class);
    BalanceRequestEvent balanceRequestEvent = BalanceRequestEvent.builder()
        .partyId(betRequest.getPartyId())
        .playerCurrency(betRequest.getCurrency())
        .brandId(betRequest.getBrandId())
        .platformCode(betRequest.getPlatformCode())
        .gameId(betRequest.getGameId())
        .numDecimalPart(1) //TODO hardcoded
        .build();

    log.info("Bet request to kafka will be sent, correlationId: {}", correlationId);

    String req = null;

    try {
      req = objectMapper.writeValueAsString(balanceRequestEvent);
    } catch (Exception e) {
      log.error("Error while serializing request", e);
    } //TODO hardcode


    producerTemplate.sendBodyAndHeader("kafka:balanceRequest", req, CORRELATION_ID, correlationId);
    return betRequest;
  }

  public BetRequestEvent bet(BetBucket betRequest) {
    log.info("Bet amount distribution: {}", betRequest);
    //TODO write logic which will according to the balance distribute amounts
    return BetRequestEvent.builder()
        .partyId(betRequest.getPartyId())
        .accountId(betRequest.getAccountId())
        .brandId(betRequest.getBrandId())
        .platformId(betRequest.getPlatformId())
        .platformCode(betRequest.getPlatformCode())
        .sportbook(betRequest.isSportbook())
        .gameInfoId(betRequest.getGameInfoId())
        .gameId(betRequest.getGameId())
        .platformGameTranId(betRequest.getPlatformGameTranId())
        .platformTranId(betRequest.getPlatformTranId())

        .currency(betRequest.getCurrency())
        .real(new BigDecimal("100"))
        .releasedBonus(new BigDecimal("200"))
        .playableBonus(new BigDecimal("300"))
        .build();
  }
}
