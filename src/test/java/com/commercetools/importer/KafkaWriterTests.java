package com.commercetools.importer;

import com.commercetools.importer.model.Product;
import com.commercetools.importer.service.KafkaWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaWriterTests {
	private Product product;

	@Mock
	KafkaTemplate<String, Product> kafkaTemplate;

	@InjectMocks
	private KafkaWriter kafkaWriter =	new KafkaWriter();

	@Test
	public void kafkaWriter_test() throws Exception {

        Product product1 = new Product();
        Product product2 = new Product();

        kafkaWriter.write(Arrays.asList(product1, product2));

        verify(kafkaTemplate, times(2)).sendDefault(any());

	}
}
