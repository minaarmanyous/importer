package com.commercetools.importer.service;

import com.commercetools.importer.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) throws Exception {
        product.setRequestDate(LocalDateTime.now());
        return product;
    }
}
