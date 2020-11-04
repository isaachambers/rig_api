package com.rigapi.service;

import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Customer createCustomer(Customer customer);

  Page<Order> getCustomerOrdersById(int customerId, Pageable pageable);
}