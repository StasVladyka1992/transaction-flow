package com.gamingtec.services.wallet.service.cache;

import java.util.Optional;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService{
  private final CacheManager cacheManager;

  @Nullable
  @Override
  public <T> T getValue(String cacheName, String key, Class<T> clazz) {
    return Optional.ofNullable(cacheManager.getCache(cacheName))
        .map(c -> c.get(key, clazz))
        .orElse(null);
  }

  @Override
  public void putValue(String cacheName, String key, Object value) {
    var cache = cacheManager.getCache(cacheName);
    if (value != null && cache != null) {
      cache.put(key, value);
      log.info("Value cached in {} cache : key: {}, value: {}", cacheName, key, value);
    }
  }

  @Override
  public void evictValue(String cacheName, String key) {
    Optional.ofNullable(cacheManager.getCache(cacheName))
        .ifPresent(cache -> {
          cache.evict(key);
          log.info("Cache {} invalidated for key: {}", cacheName, key);
        });
  }
}
