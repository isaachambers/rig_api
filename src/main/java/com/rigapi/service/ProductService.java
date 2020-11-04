package com.rigapi.service;

import com.rigapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Product createProduct(Product p);

  Page<Product> getAllProducts(Pageable pageable);
}