package com.rigapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;


import com.rigapi.IntegrationTest;
import com.rigapi.entity.Product;
import com.rigapi.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ProductServiceTest extends IntegrationTest {

  @Autowired
  private ProductService productService;

  @DisplayName("Should create new product")
  @Test
  void shouldCreateProduct() {
    Product product = new Product("The Great Gatsby", "F. Scott Fitzgerald", 12);

    Product createdProduct = productService.createProduct(product);

    assertThat(createdProduct.getId()).isNotNull();
  }


  @DisplayName("Should get all products")
  @Test
  void shouldGetAllProducts() {
    Product product1 = new Product("The Great Gatsby", "F. Scott Fitzgerald", 12);
    Product product2 = new Product("An Occurrence at Owl Creek Bridge One of the Missing", "Ambrose Bierce", 12);
    productService.createProduct(product2);
    productService.createProduct(product1);

    Pageable twoElementPage = PageRequest.of(0, 2);
    Page<Product> allProducts = productService.getAllProducts(twoElementPage);

    assertThat(allProducts.getTotalElements()).isEqualTo(2);
    assertThat(allProducts.getContent().size()).isEqualTo(2);
  }
}