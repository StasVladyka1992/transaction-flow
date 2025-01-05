package com.gamingtec.services.wallet.controller.wallet.internal;

import com.gamingtec.services.wallet.controller.wallet.internal.mapper.WalletGrpcMapper;
import com.gamingtec.services.wallet.service.wallet.api.WalletService;
import com.gamingtec.services.wallet.service.wallet.api.model.BalanceReq;
import com.gamingtec.wallet.WalletApiGrpc;
import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class WalletController extends WalletApiGrpc.WalletApiImplBase {
  private final WalletService walletService;
  private final WalletGrpcMapper walletGrpcMapper;

  @Override
  public void balanceRequest(WalletMessages.BalanceRequestGrpc grpcReq,
                             StreamObserver<Empty> responseObserver) {
    try {
      BalanceReq req = walletGrpcMapper.mapToBalanceReq(grpcReq);
      log.info("Received balance request: {}", req);
//      walletService.balanceRequest(req);
    } catch (Exception e) {
      handleErrorResult(responseObserver, e.getMessage());
    }
  }

  private <T> void handleErrorResult(StreamObserver<T> responseObserver, String message) {
    log.error("Call finished with error: {}", message);
    responseObserver.onError(Status.INTERNAL.withDescription(message).asException());
  }
}

