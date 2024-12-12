package com.example.providerservice.controller;

import com.example.providerservice.controller.dto.ProviderBalanceDto;
import com.example.providerservice.controller.dto.ProviderBalanceReqDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/provider1/")
@RequiredArgsConstructor
public class ProviderController {
    private final KafkaTemplate<String, ProviderBalanceReqDto> balanceKafkaTemplate;

    @GetMapping("/balance")
    public ProviderBalanceDto getBalance(ProviderBalanceReqDto dto) {
        try {
            CompletableFuture<SendResult<String, ProviderBalanceReqDto>>
                balance = balanceKafkaTemplate.send("balance-topic", dto);
            balance.get();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return new ProviderBalanceDto();
    }
}
