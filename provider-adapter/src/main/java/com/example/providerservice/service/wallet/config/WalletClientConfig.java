package com.example.providerservice.service.wallet.config;

import com.gamingtec.services.walletapi.WalletClient;
import com.gamingtec.services.walletgrpcclient.WalletGrpcClientImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class WalletClientConfig {
  @Bean
  private WalletClient walletClient() {
    return new WalletGrpcClientImpl("127.0.0.1", 9898);
  }
}
