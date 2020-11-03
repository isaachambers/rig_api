package com.rigapi.service.impl;

import com.rigapi.entity.Product;
import com.rigapi.repository.ProductRepository;
import com.rigapi.service.ProductService;
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
  public Product createProduct(Product p) {
    return productRepository.save(p);
  }

  @Override
  public Page<Product> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }
}