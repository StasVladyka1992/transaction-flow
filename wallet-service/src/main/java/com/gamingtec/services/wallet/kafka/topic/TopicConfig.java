package com.gamingtec.services.wallet.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
  @Bean
  public NewTopic cashBalance() {
    return TopicBuilder.name("cash-balance")
        .partitions(3)
        .compact()
        .build();
  }

  @Bean
  public NewTopic bonusBalance() {
    return TopicBuilder.name("bonus-balance")
        .partitions(3)
        .compact()
        .build();
  }
}
