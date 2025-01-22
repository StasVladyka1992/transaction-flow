package com.gamingtec.services.wallet.route.model;

import com.gamingtec.services.event.dto.BalanceEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BetResponseBucket {
  private long transactionId;
  private BalanceEvent balance;
}
