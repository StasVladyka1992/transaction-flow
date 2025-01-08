package com.gamingtec.services.provider.route.adapter.controller;

import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceDto;
import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceReqDto;
import com.gamingtec.services.provider.kafka.publisher.BalancePublisher;
import com.gamingtec.services.provider.service.wallet.WalletService;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
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
  private final WalletService walletService;
  private final ProducerTemplate producerTemplate;
  private final CamelContext camelContext;

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

  @GetMapping("camel/balance")
  public BalanceDto getCamelBalance(@RequestBody BalanceReqDto dto) {
    WalletMessages.BalanceGrpc resp = producerTemplate.requestBody("direct:balance", dto, WalletMessages.BalanceGrpc.class);
    return null;
  }
}
