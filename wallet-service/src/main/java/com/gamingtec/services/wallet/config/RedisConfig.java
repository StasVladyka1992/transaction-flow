package com.gamingtec.services.wallet.config;

//@RequiredArgsConstructor
//@Configuration
//public class RedisConfig {
//
//  private final RedisProperties redisProperties;
//
//  @Bean
//  protected LettuceConnectionFactory redisConnectionFactory() {
//
//    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//        .readFrom(ReadFrom.REPLICA_PREFERRED)
//        .build();
//
//    RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//        .master(redisProperties.getSentinel().getMaster());
//
//    redisProperties.getSentinel().getNodes()
//        .forEach(s -> sentinelConfig.sentinel(s.split(":")[0], Integer.valueOf(s.split(":")[1])));
//    sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
//    return new LettuceConnectionFactory(sentinelConfig, clientConfig);
//  }
//
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate() {
//    final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
//    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//    redisTemplate.setValueSerializer(new StringRedisSerializer());
//    redisTemplate.setConnectionFactory(redisConnectionFactory());
//    return redisTemplate;
//  }
//
//}