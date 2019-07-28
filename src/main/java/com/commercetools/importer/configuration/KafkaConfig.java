package com.commercetools.importer.configuration;

import com.commercetools.importer.model.Product;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, Product> kafkaTemplate(ProducerFactory producerFactory) {
        KafkaTemplate kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic(kafkaProperties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }

    @Bean
    public JsonSerializer jsonSerializer() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.setAddTypeInfo(false);
        return jsonSerializer;
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties(), new StringSerializer(), jsonSerializer());
    }
}
