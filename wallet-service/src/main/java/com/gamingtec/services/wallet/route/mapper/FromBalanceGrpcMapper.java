package com.gamingtec.services.wallet.route.mapper;

import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class FromBalanceGrpcMapper {
  public static WalletMessages.BalanceGrpc mapToGrpc() {
    return WalletMessages.BalanceGrpc.newBuilder()
        .setBalance(WalletMessages.DecimalValue.newBuilder()
            .setValue(ByteString.copyFromUtf8("200"))
            .setScale(2)
            .setPrecision(2)
            .build())
        .build();
  }
}
