package com.rigapi.service;

import com.rigapi.entity.Order;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import com.rigapi.web.response.OrderDetailResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  Order createOrder(CreateOrderRequest request);

  Page<CustomerOrdersResponse> getCustomerOrdersResponse(Page<Order> orders, Pageable pageable);

  List<OrderDetailResponse> getOrderDetails(int orderId);

  CustomerOrdersResponse getOrderResponse(int orderId);

  Page<Order> getAllOrders(Pageable pageable);
}