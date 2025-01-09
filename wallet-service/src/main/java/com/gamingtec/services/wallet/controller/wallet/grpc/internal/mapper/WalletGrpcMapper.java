package com.gamingtec.services.wallet.controller.wallet.grpc.internal.mapper;

import com.gamingtec.services.wallet.service.balance.model.BalanceRequest;
import com.gamingtec.wallet.WalletMessages;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletGrpcMapper {

  default BalanceRequest mapToBalanceReq(WalletMessages.BalanceRequestGrpc grpc){
    return BalanceRequest.builder().build();
  }
}
