package com.rigapi.web.controller;

import com.rigapi.entity.Order;
import com.rigapi.service.OrderService;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import com.rigapi.web.response.OrderDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orders")
@Api(value = "orders", description = "Creating customer orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Transactional
  @PostMapping("")
  @ApiOperation(value = "Create new customer order",
      notes = "This method creates a new customer order in the system",
      authorizations = {@Authorization(value = "jwtToken")})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully created customer order"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public Order createCustomerOrder(@Valid @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(request);
  }

  @GetMapping("/{orderId}/details")
  @ApiOperation(value = "Retrieves list of order details",
      authorizations = {@Authorization(value = "jwtToken")},
      response = OrderDetailResponse.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved order details"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public List<OrderDetailResponse> getOrderDetails(@PathVariable int orderId) {
    return orderService.getOrderDetails(orderId);
  }

  @GetMapping("/{orderId}")
  @ApiOperation(value = "Retrieves Order Details",
      authorizations = {@Authorization(value = "jwtToken")},
      response = CustomerOrdersResponse.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved order"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public CustomerOrdersResponse getOrder(@PathVariable int orderId) {
    return orderService.getOrderResponse(orderId);
  }

  @GetMapping("")
  @ApiOperation(value = "Retrieves list of all orders",
      authorizations = {@Authorization(value = "jwtToken")},
      response = CustomerOrdersResponse.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved available orders ordered by the most recent"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  public Page<CustomerOrdersResponse> getAllOrders(Pageable pageable) {
    Page<Order> orders = orderService.getAllOrders(pageable);
    return orderService.getCustomerOrdersResponse(orders, pageable);
  }
}
