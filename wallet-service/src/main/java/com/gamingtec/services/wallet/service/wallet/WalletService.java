package com.gamingtec.services.wallet.service.wallet;

import com.gamingtec.services.event.dto.BetRequestEvent;
import com.gamingtec.services.wallet.route.model.BetBucket;
import com.gamingtec.services.wallet.route.model.BetRequest;
import org.apache.camel.Header;

public interface WalletService {
  BetRequest getBalanceForBet(BetRequest betRequest, String correlationId);

  BetRequestEvent bet(BetBucket betRequest, @Header("correlationId") String correlationId);
}
