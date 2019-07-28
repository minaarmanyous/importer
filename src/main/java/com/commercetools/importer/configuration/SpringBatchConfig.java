package com.commercetools.importer.configuration;

import com.commercetools.importer.model.Product;
import com.commercetools.importer.service.ProductWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private static final int LINES_TO_SKIP = 1;
    private static final String[] FILE_COLUMN_NAMES = {"UUID", "Name", "Description", "provider", "available", "MeasurementUnits"};
    private static final String ROW_DELIMITER = ",";

    @Bean
    public JobBuilder jobBuilder(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("Products-Load")
                .incrementer(new RunIdIncrementer());
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<Product> itemReader,
                     ItemProcessor<Product, Product> itemProcessor, ProductWriter<Product> itemWriter) {
        return stepBuilderFactory.get("Products-Batch-Load")
                .<Product, Product>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Product> itemReader(@Value("${products-file-name}") Resource resource) {

        FlatFileItemReader<Product> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("Products-Reader");
        flatFileItemReader.setLinesToSkip(LINES_TO_SKIP);
        flatFileItemReader.setStrict(false);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Product> lineMapper() {

        DefaultLineMapper<Product> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(ROW_DELIMITER);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(FILE_COLUMN_NAMES);

        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}


