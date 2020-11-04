package com.rigapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigapi.entity.listener.OrderDetailEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "order_detail")
@EntityListeners(OrderDetailEntityListener.class)
public class OrderDetail extends BaseAuditEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_detail_id", nullable = false)
  private int id;

  @Column(name = "quantity", nullable = false)
  @Min(1)
  private int quantity;

  @OneToOne
  @JoinColumn(name = "product_id")
  @JsonIgnore
  private Product product;

  @OneToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
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

  @Override
  public String toString() {
    return "OrderDetail{" +
        "id=" + id +
        ", quantity=" + quantity +
        ", product=" + product +
        '}';
  }
}