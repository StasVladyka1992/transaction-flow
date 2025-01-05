package com.gamingtec.services.wallet.kafka.publisher.api;


import com.gamingtec.services.event.dto.BonusBalanceReqEvent;

public interface BonusPublisher {
  void sendBonusBalanceReqEvent(BonusBalanceReqEvent event);
}
