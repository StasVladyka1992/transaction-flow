package com.gamingtec.services.provider.kafka.publisher;

import com.gamingtec.services.provider.controller.dto.BalanceReqDto;

public interface BalancePublisher {
  void sendBalanceReqEvent(BalanceReqDto event);
}
