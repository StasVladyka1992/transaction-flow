package com.gamingtec.services.provider.service;

import com.gamingtec.services.provider.route.dto.BalanceDto;
import com.gamingtec.services.provider.route.dto.BalanceReqDto;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestWalletServiceImpl implements TestWalletService {

  @Override
  public BalanceDto getBalance(BalanceReqDto reqDto) {
    return BalanceDto.builder()
        .partyId(reqDto.getPartyId())
        .balance(BigDecimal.TEN)
        .build();
  }
}
