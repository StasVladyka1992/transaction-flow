package com.example.providerservice.controller;

import com.example.providerservice.controller.dto.ProviderBalanceDto;
import com.example.providerservice.controller.dto.ProviderBalanceReqDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/provider1/")
@RequiredArgsConstructor
public class ProviderController {
  private final KafkaTemplate<String, String> balanceKafkaTemplate;

  @PostMapping("/balance")
  public ProviderBalanceDto getBalance(@RequestBody ProviderBalanceReqDto dto) {
    try {
      CompletableFuture<SendResult<String, String>>
          balance = balanceKafkaTemplate.send("balance-topic", "fff");
//      ba/lance.get();.
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return new ProviderBalanceDto();
  }
}
