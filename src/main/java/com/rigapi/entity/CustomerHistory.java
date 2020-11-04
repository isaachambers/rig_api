package com.rigapi.entity;

import com.rigapi.domain.Action;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CustomerHistory extends BaseAuditEntity<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_customer_history_customer"))
  private Customer customer;

  @Column(columnDefinition="TEXT")
  private String customerDetails;

  @Enumerated(EnumType.STRING)
  private Action action;

  public CustomerHistory() {
  }

  public CustomerHistory(Customer customer, Action action) {
    this.customer = customer;
    this.customerDetails = customer.toString();
    this.action = action;
  }
}
