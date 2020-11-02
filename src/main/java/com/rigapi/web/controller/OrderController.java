package com.rigapi.web.controller;

import com.rigapi.entity.Order;
import com.rigapi.service.OrderService;
import com.rigapi.web.request.CreateOrderRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

  private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create new customer order", notes = "This method creates a new customer order in the system",
      authorizations = {@Authorization(value = "jwtToken")})
  public ResponseEntity<?> createCustomerOrder(@Valid @RequestBody CreateOrderRequest request) {
    try {
      Order order = orderService.createOrder(request);
      return new ResponseEntity<>(order, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
