package com.rigapi.entity.listener;

import com.rigapi.domain.Action;
import com.rigapi.entity.Product;
import com.rigapi.entity.ProductHistory;
import com.rigapi.service.BeanUtilService;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

public class ProductEntityListener {

  @PrePersist
  public void prePersist(Product target) {
    perform(target, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(Product target) {
    perform(target, Action.UPDATED);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  void perform(Product target, Action action) {
    EntityManager entityManager = BeanUtilService.getBean(EntityManager.class);
    entityManager.persist(new ProductHistory(target, action));
  }
}