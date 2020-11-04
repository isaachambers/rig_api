package com.rigapi.testmixin;

import static com.rigapi.testmixin.DatabaseCleaningTestMixin.Private.orderRepository;
import static com.rigapi.testmixin.DatabaseCleaningTestMixin.Private.productRepository;
import static com.rigapi.testmixin.DatabaseCleaningTestMixin.Private.customerRepository;

import com.rigapi.repository.CustomerRepository;
import com.rigapi.repository.OrderRepository;
import com.rigapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface DatabaseCleaningTestMixin {

  class Private {
    static OrderRepository orderRepository;
    static ProductRepository productRepository;
    static CustomerRepository customerRepository;
  }

  @Autowired
  default void databaseCleaningTestMixinDependencies(
      OrderRepository orderRepository,
      ProductRepository productRepository,
      CustomerRepository customerRepository
  ) {
    Private.orderRepository = orderRepository;
    Private.productRepository = productRepository;
    Private.customerRepository = customerRepository;
  }

  default void cleanDatabase() {
    orderRepository.deleteAll();
    productRepository.deleteAll();
    customerRepository.deleteAll();
  }
}
