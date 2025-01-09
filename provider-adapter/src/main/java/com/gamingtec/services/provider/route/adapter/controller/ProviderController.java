package com.gamingtec.services.provider.route.adapter.controller;

import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceDto;
import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceReqDto;
import com.gamingtec.wallet.WalletMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "api/")
@RequiredArgsConstructor
public class ProviderController {
  private final ProducerTemplate producerTemplate;

  @GetMapping("camel/balance")
  public BalanceDto getCamelBalance(@RequestBody BalanceReqDto dto) {
    WalletMessages.BalanceGrpc resp =
        producerTemplate.requestBody("direct:balance", dto, WalletMessages.BalanceGrpc.class);
    return new BalanceDto();
  }
}
