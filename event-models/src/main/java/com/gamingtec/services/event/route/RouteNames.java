package com.gamingtec.services.event.route;

public interface RouteNames {
  String DIRECT_GRPC_BALANCE_REQUEST = "direct:grpcBalanceRequest";
  String DIRECT_GRPC_BET_REQUEST = "direct:grpcBetRequest";
  String SEDA_AGGREGATED_BALANCE = "seda:aggregatedBalance";

  String KAFKA_BALANCE = "kafka:balance?brokers=localhost:9095";
  String KAFKA_BALANCE_REQUEST = "kafka:balanceRequest?brokers=localhost:9095";
  String KAFKA_BET_REQUEST = "kafka:betRequest?brokers=localhost:9095";
  String KAFKA_WAGER_REQUEST = "kafka:wagerRequest?brokers=localhost:9095";
  String KAFKA_WAGER_RESPONSE = "kafka:wagerRequest?brokers=localhost:9095";
}
