package com.gamingtec.services.wallet.service.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonSerDerConfig {
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules(); // Registers modules like Java Time
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }

  @Bean
  public JacksonDataFormat jacksonDataFormat(ObjectMapper objectMapper) {
    JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
    jacksonDataFormat.setObjectMapper(objectMapper);
    return jacksonDataFormat;
  }
}
