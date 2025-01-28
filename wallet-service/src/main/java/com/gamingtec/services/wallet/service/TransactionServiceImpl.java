package com.gamingtec.services.wallet.service;

import com.gamingtec.services.event.dto.BalanceEvent;
import com.gamingtec.services.event.dto.BetResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  @Override
  public BetResponseEvent createBetTransaction(BalanceEvent balanceEvent) {
    log.info("Bet transaction created");
    return BetResponseEvent.builder()
        .transactionId(1L)
        .balance(balanceEvent)
        .build();
  }
}
