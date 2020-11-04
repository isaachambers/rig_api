package com.rigapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rigapi.entity.listener.CustomerEntityListener;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@EntityListeners(CustomerEntityListener.class)
public class Customer extends BaseAuditEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "customer_id", nullable = false)
  private int id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "address", nullable = false)
  private String address;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Order> orders;

  public Customer() {
  }

  public Customer(String firstName, String lastName, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", address='" + address + '\'' +
        ", orders=" + orders +
        '}';
  }
}