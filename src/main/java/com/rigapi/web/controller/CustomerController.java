package com.rigapi.web.controller;

import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.service.CustomerService;
import com.rigapi.web.request.CreateCustomerRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import com.rigapi.web.response.OrderDetailResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
  private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
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

      List<CustomerOrdersResponse> responses = new ArrayList<>();
      orders.getContent().forEach(order -> {

        CustomerOrdersResponse response = new CustomerOrdersResponse();
        response.setCreationTime(order.getCreationTime());
        response.setUpdatedTime(order.getUpdatedTime());
        response.setOrderStatus(order.getOrderStatus());

        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        order.getDetails().forEach(orderDetail -> {
          OrderDetailResponse detail = new OrderDetailResponse();
          detail.setProductId(orderDetail.getId());
          detail.setProductName(orderDetail.getProduct().getName());
          detail.setQuantity(orderDetail.getQuantity());
        });

        response.setDetails(orderDetailResponseList);
        responses.add(response);
      });

      Page<CustomerOrdersResponse> pages = new PageImpl<>(responses, pageable, orders.getTotalElements());
      return new ResponseEntity<>(pages, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.error("error", ex);
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
