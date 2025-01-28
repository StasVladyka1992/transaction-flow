package com.gamingtec.services.wallet.service.cache;

import javax.annotation.Nullable;

public interface CacheService {
  @Nullable
  <T> T getValue(String cacheName, String key, Class<T> clazz);

  void putValue(String cacheName, String key, Object value);

  void evictValue(String cacheName, String key);
}
