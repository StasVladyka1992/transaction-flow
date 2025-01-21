package com.gamingtec.services.provider.route.mapper;

import com.gamingtec.services.provider.route.dto.BetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BetResponseMapper {
  private final BalanceMapper balanceMapper;

  public BetResponseDto toDto(){
    return new BetResponseDto();
  }
}
