package com.rigapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigapi.domain.OrderStatus;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "order_tbl")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_id")
  private int id;

  @JoinColumn(name = "customer_id", nullable = false)
  @ManyToOne
  @JsonIgnore
  private Customer customer;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private Set<OrderDetail> details;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "CREATED_TIME")
  private Date creationTime;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "UPDATED_TIME")
  private Date updatedTime;

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

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }
}
