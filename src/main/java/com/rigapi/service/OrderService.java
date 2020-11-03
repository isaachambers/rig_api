package com.rigapi.service;

import com.rigapi.domain.OrderStatus;
import com.rigapi.entity.Order;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  Order createOrder(CreateOrderRequest request);

  void changeOrderStatus(OrderStatus orderStatus, Order order);

  Page<CustomerOrdersResponse> getCustomerOrdersResponse(Page<Order> orders, Pageable pageable);
}
