package com.rigapi.web.controller;

import com.rigapi.entity.Product;
import com.rigapi.service.ProductService;
import com.rigapi.web.request.CreateOrUpdateProductRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

  private final ProductService productService;
  private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @GetMapping("")
  @ApiOperation(value = "Get all current products", notes = "List of all products in inventory", authorizations = {@Authorization(value = "jwtToken")})
  public ResponseEntity<?> getAll(Pageable pageable) {
    try {
      Page<Product> products = productService.getAllProducts(pageable);
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create Product", notes = "This method creates a new product in store", authorizations = {@Authorization(value = "jwtToken")})
  public ResponseEntity<?> createProduct(@Valid @RequestBody CreateOrUpdateProductRequest request) {
    try {
      Optional<Product> product = productService.createProduct(new Product(request.getName(), request.getAuthor(), request.getQuantity()));
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}