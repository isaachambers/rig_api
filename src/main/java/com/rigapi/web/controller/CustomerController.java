package com.rigapi.web.controller;

import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.service.CustomerService;
import com.rigapi.service.OrderService;
import com.rigapi.web.request.CreateCustomerRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

  private final CustomerService customerService;
  private final OrderService orderService;

  public CustomerController(CustomerService customerService, OrderService orderService) {
    this.customerService = customerService;
    this.orderService = orderService;
  }


  @Transactional
  @PostMapping("")
  @ApiOperation(
      value = "Create Customer",
      notes = "This method creates a new customer in the system",
      authorizations = {@Authorization(value = "jwtToken")},
      response = Customer.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successfully created customer"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @ResponseStatus(HttpStatus.CREATED)
  public Customer createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
    return customerService.createCustomer(new Customer(request.getFirstName(), request.getLastName(), request.getAddress()));

  }

  @GetMapping("/{customerId}/orders")
  @ApiOperation(value = "Retrieves list of customer orders",
      authorizations = {@Authorization(value = "jwtToken")},
      response = CustomerOrdersResponse.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved customer orders"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @ResponseStatus(HttpStatus.OK)
  public Page<CustomerOrdersResponse> getCustomerOrders(@PathVariable int customerId, Pageable pageable) {
    Page<Order> orders = customerService.getCustomerOrdersById(customerId, pageable);
    return orderService.getCustomerOrdersResponse(orders, pageable);
  }
}