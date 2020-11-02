package com.rigapi.service;

import com.rigapi.entity.Product;
import com.rigapi.repository.ProductRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Optional<Product> getProductById(int productId) {
    return productRepository.findById(productId);
  }

  public Optional<Product> createProduct(Product product) {
    return Optional.of(productRepository.save(product)).orElseThrow(RuntimeException::new) ;
  }
}