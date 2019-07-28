package com.commercetools.importer.service;

import com.commercetools.importer.model.Product;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

@Component
public class KafkaWriter implements ProductWriter<Product> {

    @Autowired
    KafkaTemplate<String, Product> kafkaTemplate;

    private static Logger log = getLogger(KafkaWriter.class);

    @Override
    public void write(List<? extends Product> products) throws Exception {
        products.forEach(product -> {
            kafkaTemplate.sendDefault(product);
            log.info(product);
        });

    }
}
