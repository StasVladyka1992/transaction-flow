package com.gamingtec.services.walletgrpcclient;

import com.gamingtec.services.walletapi.WalletClient;
import com.gamingtec.wallet.WalletApiGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class WalletGrpcClientImpl implements WalletClient {
  private WalletApiGrpc.WalletApiBlockingStub client;

  public WalletGrpcClientImpl(String host, int port) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext()
        .build();
    this.client = WalletApiGrpc.newBlockingStub(managedChannel);
  }
}
