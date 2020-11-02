package com.rigapi.web.controller;

import com.rigapi.entity.Product;
import com.rigapi.service.ProductService;
import com.rigapi.web.request.CreateOrUpdateProductRequest;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{productId}")
  public ResponseEntity<?> getProducts(@PathVariable int productId) {
    try {
      Optional<Product> product = productService.getProductById(productId);
      if (product.isPresent()) {
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create Product", notes = "This method creates a new product in store")
  public ResponseEntity<?> createProduct(@Valid @RequestBody CreateOrUpdateProductRequest request) {
    try {
      Optional<Product> product = productService.createProduct(new Product(request.getName(), request.getQuantity()));
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @PutMapping("/{productId}")
  @ApiOperation(value = "Update Product", notes = "This method updates a product in store")
  public ResponseEntity<> updateProduct(@PathVariable int productId,
                                        @Valid @RequestBody CreateOrUpdateProductRequest request) {
    try {
      Optional<Product> productOptional = productService.getProductById(productId);
      if (productOptional.isPresent()) {
        Product product = productOptional.get();
        product.setAuthor(request.getAuthor());
        product.setQuantity(request.getQuantity());
        productService.createProduct(product);

        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{productId}")
  @ApiOperation(value = "Delete a single product")
  public void delete(@PathVariable int productId) {
    productService.delete(productId);
  }
}
