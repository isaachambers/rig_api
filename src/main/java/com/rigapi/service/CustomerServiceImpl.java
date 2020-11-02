package com.rigapi.service;

import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.exception.CustomerNotFoundException;
import com.rigapi.repository.CustomerRepository;
import com.rigapi.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository,
                             OrderRepository orderRepository) {
    this.customerRepository = customerRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Page<Order> getCustomerOrdersById(int customerId, Pageable pageable) {
    Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    return orderRepository.findByCustomerId(customer.getId(), pageable);
  }

}