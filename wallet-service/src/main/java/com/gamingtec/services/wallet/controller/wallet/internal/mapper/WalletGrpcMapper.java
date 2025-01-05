package com.gamingtec.services.wallet.controller.wallet.internal.mapper;

import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import com.gamingtec.wallet.WalletMessages;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletGrpcMapper {

  default BalanceReq mapToBalanceReq(WalletMessages.BalanceRequestGrpc grpc){
    return BalanceReq.builder().build();
  }
}
