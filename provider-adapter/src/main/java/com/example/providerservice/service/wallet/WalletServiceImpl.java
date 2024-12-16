package com.example.providerservice.service.wallet;

import com.gamingtec.services.walletapi.WalletClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class WalletServiceImpl implements WalletService {
  private final WalletClient walletClient;
}
