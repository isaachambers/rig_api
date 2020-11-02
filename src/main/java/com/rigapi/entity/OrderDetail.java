package com.rigapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_detail_id", nullable = false)
  private int id;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private Product product;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}