package soma.everyonepick.api.core.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

public interface KafkaProducerConfig {
    ProducerFactory<String, ?> producerFactory();

    KafkaTemplate<String, ?> kafkaTemplate();
}
