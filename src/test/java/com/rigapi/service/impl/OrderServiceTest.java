package com.rigapi.service.impl;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.rigapi.IntegrationTest;
import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.entity.Product;
import com.rigapi.service.CustomerService;
import com.rigapi.service.OrderService;
import com.rigapi.service.ProductService;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.request.OrderDetailsRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import com.rigapi.web.response.OrderDetailResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class OrderServiceTest extends IntegrationTest {

  @Autowired
  private CustomerService customerService;
  @Autowired
  private ProductService productService;
  @Autowired
  private OrderService orderService;

  @DisplayName("Should Create New Order")
  @Test
  void shouldCreateNewOrder() {
    CreateOrderRequest request = getCreateOrderRequest();

    Order createdOrder = orderService.createOrder(request);

    assertThat(createdOrder).isNotNull();
    assertThat(createdOrder.getId()).isNotNull();
    assertThat(createdOrder.getDetails().size()).isEqualTo(2);
  }

  @DisplayName("Should convert a page of orders to a detailed page with order details")
  @Test
  void shouldProperlyConvertPagedOrdersToDetailedOrderDetails() {
    CreateOrderRequest request = getCreateOrderRequest();
    Order createdOrder = orderService.createOrder(request);
    Page<Order> ordersPage = new PageImpl<>(Collections.singletonList(createdOrder));
    Pageable twoElementPage = PageRequest.of(0, 1);

    Page<CustomerOrdersResponse> responses = orderService.getCustomerOrdersResponse(ordersPage, twoElementPage);

    assertThat(responses.getTotalElements()).isEqualTo(ordersPage.getTotalElements());
  }


  @DisplayName("Should list order details for a given order")
  @Test
  void shouldReturnListOfOrderDetailsForOrder() {
    CreateOrderRequest request = getCreateOrderRequest();

    Order createdOrder = orderService.createOrder(request);
    List<OrderDetailResponse> orderDetailResponseList = orderService.getOrderDetails(createdOrder.getId());

    assertThat(orderDetailResponseList.size()).isEqualTo(2);
    assertThat(orderDetailResponseList).extracting("productName")
        .containsExactlyInAnyOrder("The Great Gatsby", "An Occurrence at Owl Creek Bridge One of the Missing");
  }


  @DisplayName("Should return order details")
  @Test
  void shouldReturnOrderDetailsWithOrderId() {
    CreateOrderRequest request = getCreateOrderRequest();

    Order createdOrder = orderService.createOrder(request);
    CustomerOrdersResponse response = orderService.getOrderResponse(createdOrder.getId());

    assertThat(response.getDetails().size()).isEqualTo(2);
    assertThat(response.getDetails()).extracting("productName")
        .containsExactlyInAnyOrder("The Great Gatsby", "An Occurrence at Owl Creek Bridge One of the Missing");
  }

  private CreateOrderRequest getCreateOrderRequest() {
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
    return request;
  }
}