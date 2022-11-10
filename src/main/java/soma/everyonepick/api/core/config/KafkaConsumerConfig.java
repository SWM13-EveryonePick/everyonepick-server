package soma.everyonepick.api.core.config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

public interface KafkaConsumerConfig {
    ConsumerFactory<String, ?> consumerFactory();
    ConcurrentKafkaListenerContainerFactory<String, ?> kafkaListenerContainerFactory();
}
