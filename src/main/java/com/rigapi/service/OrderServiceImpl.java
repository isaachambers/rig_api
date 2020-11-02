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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
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
  public Order createOrder(CreateOrderRequest request) {
    Order order = new Order();
    Customer customer = getCustomer(request.getCustomerId());

    order.setCustomer(customer);
    List<OrderDetail> orderDetails = request.getDetailsList().stream().map(detail -> {
      //Check if product exists
      Product product = productRepository.findById(detail.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product does not exist"));
      // Check if quantity ordered for does not exceed quantity in store
      if (product.getQuantity() - detail.getQuantity() < 0) {
        throw new OutOfStockException(String.format("Order cannot be fulfilled because only %s items are left in stock", product.getQuantity()));
      }
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setProduct(product);
      orderDetail.setQuantity(detail.getQuantity());
      orderDetail.setOrder(order);
      return orderDetail;
    }).collect(Collectors.toList());
    order.setCreationTime(new Date());
    order.setUpdatedTime(new Date());
    order.setOrderStatus(OrderStatus.PENDING);
    order.setDetails(new HashSet<>(orderDetails));
    customer.getOrders().add(order);
    customerRepository.save(customer);
    return orderRepository.save(order);
  }

  public Customer getCustomer(int customerId) {
    return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
  }
}
