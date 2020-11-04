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
public class ProductHistory extends BaseAuditEntity<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_product_history_product"))
  private Product product;

  @Column(columnDefinition = "TEXT")
  private String productDetails;

  @Enumerated(EnumType.STRING)
  private Action action;

  public ProductHistory() {
  }

  public ProductHistory(Product product, Action action) {
    this.product = product;
    this.productDetails = product.toString();
    this.action = action;
  }
}
