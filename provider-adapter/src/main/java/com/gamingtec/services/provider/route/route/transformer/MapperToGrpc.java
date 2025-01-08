package com.gamingtec.services.provider.route.route.transformer;


import com.gamingtec.services.provider.route.adapter.controller.dto.BalanceReqDto;
import com.gamingtec.wallet.WalletMessages;
import org.springframework.stereotype.Component;

@Component
public class MapperToGrpc {

  public static WalletMessages.BalanceRequestGrpc toBalanceRequestGrpc(BalanceReqDto dto) {
    return WalletMessages.BalanceRequestGrpc.getDefaultInstance();
  }
}
