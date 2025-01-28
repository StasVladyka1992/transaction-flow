package com.gamingtec.services.wallet.service.wallet;

import static com.gamingtec.services.event.util.Header.CORRELATION_ID;
import static com.gamingtec.services.event.route.RouteNames.KAFKA_BALANCE_REQUEST;
import static com.gamingtec.services.wallet.service.cache.config.CachNames.BET_REQUEST;

import com.gamingtec.services.event.dto.BalanceRequestEvent;
import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import com.gamingtec.services.wallet.route.model.BetRequest;
import com.gamingtec.services.wallet.service.json.JsonSerDer;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.ProducerTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class WalletServiceImpl implements WalletService {
  private final JsonSerDer jsonSerDer;
  private final ProducerTemplate producerTemplate;
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public BetRequest getBalanceForBet(BetRequest betRequest, @Header("correlationId") String correlationId) {
    BalanceRequestEvent balanceRequestEvent = BalanceRequestEvent.builder()
        .partyId(betRequest.getPartyId())
        .playerCurrency(betRequest.getCurrency())
        .brandId(betRequest.getBrandId())
        .platformCode(betRequest.getPlatformCode())
        .gameId(betRequest.getGameId())
        .numDecimalPart(betRequest.getNumDecimalPart())
        .build();

    redisTemplate.opsForValue().set(BET_REQUEST + correlationId, betRequest, 30, TimeUnit.SECONDS);
    log.info("Balance request saved in redis by correlationId {}", correlationId);

    String event = jsonSerDer.mapToJson(balanceRequestEvent);
    producerTemplate.sendBodyAndHeader(KAFKA_BALANCE_REQUEST, event, CORRELATION_ID, correlationId);
    log.info("Balance request to kafka will be sent {}, correlationId: {}", balanceRequestEvent, correlationId);
    return betRequest;
  }

  @Override
  public BetRequestEvent bet(BetBucket betBucket, @Header("correlationId") String correlationId) {
    log.info("Available balance, correlationId {}, {}", correlationId, betBucket);
    BetRequest betRequest = (BetRequest) redisTemplate.opsForValue().get(BET_REQUEST + correlationId);
    log.info("Balance req was sent to redis, correlationId: {}", correlationId);

    if (betRequest == null) {
      log.error("Bet request not found by correlationId: {}", correlationId);
      throw new RuntimeException("Balance is not enough for bet, correlationId" + correlationId);
    }

    BigDecimal balance = betBucket.getTotalSum();
    if (betRequest.getAmount().compareTo(balance) > 0) {
      log.info("Balance isn't enough for bet, balance: {}, bet: {}", balance, betRequest.getAmount());
      throw new RuntimeException("Balance is not enough for bet, correlationId: " + correlationId);
    }

    return buildBetRequestEvent(betBucket, betRequest);
  }

  private BetRequestEvent buildBetRequestEvent(BetBucket betBucket, BetRequest betRequest) {
    BigDecimal bet = betRequest.getAmount();

    BigDecimal real = getBetSum(bet, betBucket.getReal());
    bet = bet.subtract(betBucket.getReal());
    BigDecimal releasedBonus = getBetSum(bet, betBucket.getReleasedBonus());
    bet = bet.subtract(releasedBonus);
    BigDecimal playableBonus = getBetSum(bet, betBucket.getPlayableBonus());

    return BetRequestEvent.builder()
        .real(real)
        .releasedBonus(releasedBonus)
        .playableBonus(playableBonus)
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
        .build();
  }

  private BigDecimal getBetSum(BigDecimal bet, BigDecimal fund) {
    BigDecimal result = fund.compareTo(bet) > 0 ? bet : fund;
    return result.compareTo(BigDecimal.ZERO) > 0 ? result : BigDecimal.ZERO;
  }
}
