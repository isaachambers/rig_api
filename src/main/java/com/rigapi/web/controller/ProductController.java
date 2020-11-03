package com.rigapi.web.controller;

import com.rigapi.entity.Product;
import com.rigapi.service.ProductService;
import com.rigapi.web.request.CreateOrUpdateProductRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("")
  @ApiOperation(
      value = "Get all current products",
      notes = "List of all products in inventory",
      authorizations = {@Authorization(value = "jwtToken")},
      response = Product.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully got all current products"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public Page<Product> getAll(Pageable pageable) {
    return productService.getAllProducts(pageable);
  }

  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create Product",
      notes = "This method creates a new product in store",
      authorizations = {@Authorization(value = "jwtToken")})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully created new product"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public Product createProduct(@Valid @RequestBody CreateOrUpdateProductRequest request) {
    return productService.createProduct(new Product(request.getName(), request.getAuthor(), request.getQuantity()));
  }
}