package com.rigapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.rigapi.IntegrationTest;
import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.entity.Product;
import com.rigapi.service.CustomerService;
import com.rigapi.service.OrderService;
import com.rigapi.service.ProductService;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.request.OrderDetailsRequest;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class CustomerServiceTest extends IntegrationTest {

  @Autowired
  private CustomerService customerService;
  @Autowired
  private ProductService productService;
  @Autowired
  private OrderService orderService;

  @DisplayName("Should create customer")
  @Test
  void shouldCreateCustomer() {
    Customer customer = new Customer();
    customer.setAddress("Upper Hill Road, California");
    customer.setFirstName("Ted");
    customer.setLastName("Bearman");

    Customer createdCustomer = customerService.createCustomer(customer);

    assertThat(createdCustomer.getId()).isNotNull();
  }

  @DisplayName("Should get orders by customer Id")
  @Test
  void shouldRetrieveOrdersByCustomerId() {
    Customer customer = new Customer();
    customer.setAddress("Upper Hill Road, California");
    customer.setFirstName("Ted");
    customer.setLastName("Bearman");
    Customer createdCustomer = customerService.createCustomer(customer);
    Product product1 = new Product("The Great Gatsby", "F. Scott Fitzgerald", 8);
    Product product2 = new Product("An Occurrence at Owl Creek Bridge One of the Missing", "Ambrose Bierce", 12);
    productService.createProduct(product2);
    productService.createProduct(product1);
    CreateOrderRequest request = new CreateOrderRequest();
    request.setCustomerId(createdCustomer.getId());
    OrderDetailsRequest gatsbyProductDetail = new OrderDetailsRequest(3, product1.getId());
    OrderDetailsRequest creekProductDetail = new OrderDetailsRequest(4, product2.getId());
    request.setDetailsList(Arrays.asList(gatsbyProductDetail, creekProductDetail));
    orderService.createOrder(request);
    Pageable twoElementPage = PageRequest.of(0, 2);

    Page<Order> customerOrders = customerService.getCustomerOrdersById(createdCustomer.getId(), twoElementPage);

    assertThat(customerOrders.getContent().size()).isEqualTo(1);
    assertThat(customerOrders.getTotalElements()).isEqualTo(1);
  }
}