package com.gamingtec.services.wallet.service.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonSerDer {
  private final ObjectMapper objectMapper;

  public String mapToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("Serialization error: {}", e.getMessage(), e);
      throw new RuntimeException("Can't serialize object: " + object);
    }
  }
}
