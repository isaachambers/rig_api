package com.rigapi.service;

import com.rigapi.entity.Product;
import com.rigapi.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Optional<Product> createProduct(Product p) {
    Product product = productRepository.save(p);
    return Optional.of(product);
  }

  @Override
  public Page<Product> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }
}