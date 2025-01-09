package com.gamingtec.services.walletgrpcclient;

import com.gamingtec.services.walletapi.WalletClient;
import com.gamingtec.services.walletapi.dto.BalanceReqDto;
import com.gamingtec.wallet.WalletApiGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WalletGrpcClientImpl implements WalletClient {
  private WalletApiGrpc.WalletApiBlockingStub client;

  public WalletGrpcClientImpl(String host, int port) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext()
        .build();
    this.client = WalletApiGrpc.newBlockingStub(managedChannel);
  }


  @Override
  public void balanceRequest(BalanceReqDto dto) {
  }
}
