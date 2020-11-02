package com.rigapi.service;

import com.rigapi.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Optional<Product> createProduct(Product p);

  Page<Product> getAllProducts(Pageable pageable);
}
