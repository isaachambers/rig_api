package com.rigapi.service;

import com.rigapi.domain.OrderStatus;
import com.rigapi.entity.Customer;
import com.rigapi.entity.Order;
import com.rigapi.entity.OrderDetail;
import com.rigapi.entity.Product;
import com.rigapi.exception.CustomerNotFoundException;
import com.rigapi.exception.OutOfStockException;
import com.rigapi.exception.ProductNotFoundException;
import com.rigapi.repository.CustomerRepository;
import com.rigapi.repository.OrderRepository;
import com.rigapi.repository.ProductRepository;
import com.rigapi.web.request.CreateOrderRequest;
import com.rigapi.web.response.CustomerOrdersResponse;
import com.rigapi.web.response.OrderDetailResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;

  public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public synchronized Order createOrder(CreateOrderRequest request) {
    Order order = new Order();
    Customer customer = getCustomer(request.getCustomerId());

    order.setCustomer(customer);
    List<OrderDetail> orderDetails = request.getDetailsList().stream().map(detail -> {
      //Check if product exists
      Product product = productRepository.findById(detail.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product does not exist"));
      // Check if quantity ordered for does not exceed quantity in store
      if (product.getQuantity() - detail.getQuantity() < 0) {
        throw new OutOfStockException(String.format("Order cannot be fulfilled because only %s items are left in stock", product.getQuantity()));
      } else {
        int currentStock = product.getQuantity() - detail.getQuantity();
        product.setQuantity(currentStock);
        productRepository.save(product);
      }
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setProduct(product);
      orderDetail.setQuantity(detail.getQuantity());
      orderDetail.setOrder(order);
      return orderDetail;
    }).collect(Collectors.toList());
    order.setCreationTime(new Date());
    order.setUpdatedTime(new Date());
    order.setOrderStatus(OrderStatus.CREATED);
    order.setDetails(new HashSet<>(orderDetails));
    customer.getOrders().add(order);
    customerRepository.save(customer);
    return orderRepository.save(order);
  }

  @Override
  public synchronized void changeOrderStatus(OrderStatus orderStatus, Order order) {
    order.setOrderStatus(orderStatus);
    orderRepository.save(order);
  }

  public Customer getCustomer(int customerId) {
    return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
  }

  @Override
  public Page<CustomerOrdersResponse> getCustomerOrdersResponse(Page<Order> orders, Pageable pageable) {
    List<CustomerOrdersResponse> responses = new ArrayList<>();
    orders.getContent().forEach(order -> {

      CustomerOrdersResponse response = new CustomerOrdersResponse();
      response.setCreationTime(order.getCreationTime());
      response.setUpdatedTime(order.getUpdatedTime());
      response.setOrderStatus(order.getOrderStatus());
      response.setOrderId(order.getId());

      List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
      order.getDetails().forEach(orderDetail -> {
        OrderDetailResponse detail = new OrderDetailResponse();
        detail.setProductId(orderDetail.getId());
        detail.setProductName(orderDetail.getProduct().getName());
        detail.setQuantityOrdered(orderDetail.getQuantity());
        orderDetailResponseList.add(detail);
      });

      response.setDetails(orderDetailResponseList);
      responses.add(response);
    });
    return new PageImpl<>(responses, pageable, orders.getTotalElements());
  }
}
