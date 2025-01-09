package com.gamingtec.services.bonusservice.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
  @Bean
  public NewTopic bonusBalance() {
    return TopicBuilder.name("bonus-balance")
        .partitions(3)
        .compact()
        .build();
  }
}
