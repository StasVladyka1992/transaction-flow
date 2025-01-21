package com.gamingtec.services.wallet.controller.wallet;

import com.gamingtec.wallet.WalletApiGrpc;
import com.gamingtec.wallet.WalletMessages;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WalletController extends WalletApiGrpc.WalletApiImplBase {


  @Override
  public void balance(WalletMessages.BalanceRequestGrpc request,
                      StreamObserver<WalletMessages.BalanceGrpc> responseObserver) {
    super.balance(request, responseObserver);
  }

  @Override
  public void bet(WalletMessages.BetRequestGrpc request,
                  StreamObserver<WalletMessages.BetResponseGrpc> responseObserver) {
    super.bet(request, responseObserver);
  }

//  @Override
//  public void balanceRequest(WalletMessages.BalanceRequestGrpc grpcReq,
//                             StreamObserver<WalletMessages.BalanceGrpc> responseObserver) {
//    consumerTemplate.receive();
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

  private <T> void handleErrorResult(StreamObserver<T> responseObserver, String message) {
    log.error("Call finished with error: {}", message);
    responseObserver.onError(Status.INTERNAL.withDescription(message).asException());
  }
}

