package com.gamingtec.services.wallet.kafka.publisher.api;


import com.gamingtec.services.event.dto.BalanceRequestEvent;

public interface BalanceRequestPublisher {
  void sendBalanceRequestEvent(BalanceRequestEvent balanceRequestEvent);
}
