package com.commercetools.importer;

import com.commercetools.importer.model.Product;
import com.commercetools.importer.service.ProductProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductProcessorTests {
	private Product product;

	@Mock
	protected StepExecution stepExecution;

	@InjectMocks
	private ProductProcessor productProcessor =	new ProductProcessor();

	@Test
	public void productProcess_test() throws Exception {

		product = new Product();
		product.setUuid("fccc13f1-f337-480b-9305-a5bb56bcaa11");

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(Instant.now().getEpochSecond()));
		JobParameters parameters = new JobParameters(maps);

		when(this.stepExecution.getJobParameters()).thenReturn(parameters);

		Product processedProduct = productProcessor.process(product);
		assertNotNull(processedProduct, "");
		assertNotNull(processedProduct.getRequestDate());
		assertNotNull(processedProduct.getUuid());
	}
}
