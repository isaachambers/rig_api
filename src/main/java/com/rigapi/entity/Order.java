package com.rigapi.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_tbl")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(table = "order_id")
  private int id;

  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @OneToMany(mappedBy = "order")
  private Set<OrderDetail> details;

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
}
