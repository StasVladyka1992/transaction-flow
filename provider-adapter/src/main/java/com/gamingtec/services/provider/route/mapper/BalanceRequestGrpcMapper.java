package com.gamingtec.services.provider.route.mapper;


import com.gamingtec.services.provider.route.dto.BalanceReqDto;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class BalanceRequestGrpcMapper {

  public static WalletMessages.BalanceRequestGrpc toGrpc(BalanceReqDto dto) {
    return WalletMessages.BalanceRequestGrpc.getDefaultInstance();
  }
}
