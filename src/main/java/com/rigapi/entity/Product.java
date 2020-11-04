package com.rigapi.entity;

import com.rigapi.entity.listener.ProductEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
@EntityListeners(ProductEntityListener.class)
public class Product extends BaseAuditEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "product_id", nullable = false)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  public Product() {
  }

  public Product(String name, String author, int quantity) {
    this.name = name;
    this.author = author;
    this.quantity = quantity;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author='" + author + '\'' +
        ", quantity=" + quantity +
        '}';
  }
}