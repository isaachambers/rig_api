package com.rigapi.web.controller;

import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.service.CustomerService;
import com.rigapi.service.OrderService;
import com.rigapi.web.request.CreateCustomerRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

  private final CustomerService customerService;
  private final OrderService orderService;
  private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  public CustomerController(CustomerService customerService, OrderService orderService) {
    this.customerService = customerService;
    this.orderService = orderService;
  }


  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create Customer", notes = "This method creates a new customer in the system", authorizations = {@Authorization(value = "jwtToken")})
  public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
    try {
      Customer customer = customerService
          .createCustomer(new Customer(request.getFirstName(), request.getLastName(), request.getAddress()));
      return new ResponseEntity<>(customer, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional
  @GetMapping("/{customerId}/orders")
  @ApiOperation(value = "Retrieves list of customer orders", authorizations = {@Authorization(value = "jwtToken")})
  public ResponseEntity<?> createCustomer(@PathVariable int customerId, Pageable pageable) {
    try {
      Page<Order> orders = customerService.getCustomerOrdersById(customerId, pageable);
      return new ResponseEntity<>(orderService.getCustomerOrdersResponse(orders, pageable), HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}