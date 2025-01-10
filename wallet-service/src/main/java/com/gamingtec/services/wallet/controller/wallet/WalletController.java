package com.gamingtec.services.wallet.controller.wallet;

import com.gamingtec.wallet.WalletApiGrpc;
import com.gamingtec.wallet.WalletMessages;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import net.devh.boot.grpc.server.service.GrpcService;

//@Slf4j
//@GrpcService
//@RequiredArgsConstructor
//public class WalletController extends WalletApiGrpc.WalletApiImplBase {
//  @Override
//  public void balanceRequest(WalletMessages.BalanceRequestGrpc grpcReq,
//                             StreamObserver<WalletMessages.BalanceGrpc> responseObserver) {
//    try {
//      log.info("Received balance request: {}", grpcReq);
//      responseObserver.onNext(WalletMessages.BalanceGrpc.newBuilder()
//          .setBalance(WalletMessages.DecimalValue.newBuilder()
//              .setValue(ByteString.copyFromUtf8("200"))
//              .setScale(2)
//              .setPrecision(2)
//              .build())
//          .build());
//      responseObserver.onCompleted();
//    } catch (Exception e) {
//      handleErrorResult(responseObserver, e.getMessage());
//    }
//  }
//
//  private <T> void handleErrorResult(StreamObserver<T> responseObserver, String message) {
//    log.error("Call finished with error: {}", message);
//    responseObserver.onError(Status.INTERNAL.withDescription(message).asException());
//  }
//}

