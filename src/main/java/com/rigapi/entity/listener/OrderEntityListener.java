package com.rigapi.entity.listener;

import com.rigapi.domain.Action;
import com.rigapi.entity.Order;
import com.rigapi.entity.OrderHistory;
import com.rigapi.service.BeanUtilService;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;

public class OrderEntityListener {

  @PrePersist
  public void prePersist(Order target) {
    perform(target, Action.INSERTED);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  void perform(Order target, Action action) {
    EntityManager entityManager = BeanUtilService.getBean(EntityManager.class);
    entityManager.persist(new OrderHistory(target, action));
  }
}