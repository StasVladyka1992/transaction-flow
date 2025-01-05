package com.gamingtec.services.wallet.kafka.publisher.api;


import com.gamingtec.services.event.dto.BalanceEvent;

public interface BalancePublisher {
  void sendBalanceEvent(BalanceEvent balanceEvent);
}
