package com.gamingtec.services.provider.controller;

import com.gamingtec.services.provider.controller.dto.BalanceDto;
import com.gamingtec.services.provider.controller.dto.BalanceReqDto;
import com.gamingtec.services.provider.kafka.listener.BalanceListener;
import com.gamingtec.services.provider.kafka.publisher.BalancePublisher;
import com.gamingtec.services.provider.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "api/")
@RequiredArgsConstructor
public class ProviderController {
  private final BalancePublisher balancePublisher;
  private final BalanceListener balanceListener;
  private final WalletService walletService;

  @GetMapping("kafka/{providerId}/balance")
  public BalanceDto getKafkaBalance(@PathVariable("providerId") Integer providerId,
                               @RequestBody BalanceReqDto dto) {
    balancePublisher.sendBalanceReqEvent(dto);
    return new BalanceDto();
  }

  @GetMapping("grpc/{providerId}/balance")
  public BalanceDto getGrpcBalance(@PathVariable("providerId") Integer providerId,
                                      @RequestBody BalanceReqDto dto) {
    walletService.getBalance(dto);
    return new BalanceDto();
  }
}
