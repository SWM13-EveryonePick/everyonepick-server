//package soma.everyonepick.api.core.config;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import soma.everyonepick.api.album.event.RedisKeyExpiredEventListener;
//
//@Slf4j
//@Configuration
//public class RedisConfig {
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    private final String PATTERN = "__keyevent@*__:expired";
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(redisHost, redisPort);
//    }
//
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory redisConnectionFactory,
//            RedisKeyExpiredEventListener expiredEventListener
//    ) {
//        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//        redisMessageListenerContainer.addMessageListener(expiredEventListener, new PatternTopic(PATTERN));
//        redisMessageListenerContainer.setErrorHandler(e -> log.error("Redis key expiration listener Error", e));
//        return redisMessageListenerContainer;
//    }
//}
