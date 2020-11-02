package com.rigapi.service;

import com.rigapi.entity.Order;
import com.rigapi.web.request.CreateOrderRequest;

public interface OrderService {

  Order createOrder(CreateOrderRequest request);

}
