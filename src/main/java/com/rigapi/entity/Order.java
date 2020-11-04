package com.rigapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigapi.domain.OrderStatus;
import com.rigapi.entity.listener.OrderEntityListener;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_tbl")
@EntityListeners(OrderEntityListener.class)
public class Order extends BaseAuditEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_id")
  private int id;

  @JoinColumn(name = "customer_id", nullable = false)
  @ManyToOne
  @JsonIgnore
  private Customer customer;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderDetail> details;

  private OrderStatus orderStatus;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Set<OrderDetail> getDetails() {
    return details;
  }

  public void setDetails(Set<OrderDetail> details) {
    this.details = details;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", details=" + details +
        ", orderStatus=" + orderStatus +
        '}';
  }
}
