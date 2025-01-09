package com.gamingtec.services.wallet.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
//  @Bean
//  public NewTopic cashBalance() {
//    return TopicBuilder.name("cash-balance")
//        .partitions(3)
//        .compact()
//        .build();
//  }

  @Bean
  public NewTopic balanceRequest() {
    return TopicBuilder.name("balance-request")
        .partitions(3)
        .compact()
        .build();
  }

  @Bean
  public NewTopic balance() {
    return TopicBuilder.name("balance")
        .partitions(3)
        .compact()
        .build();
  }
}
