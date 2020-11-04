package com.rigapi.entity.listener;

import com.rigapi.domain.Action;
import com.rigapi.entity.OrderDetail;
import com.rigapi.entity.OrderDetailHistory;
import com.rigapi.service.BeanUtilService;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;

public class OrderDetailEntityListener {

  @PrePersist
  public void prePersist(OrderDetail target) {
    perform(target, Action.INSERTED);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  void perform(OrderDetail target, Action action) {
    EntityManager entityManager = BeanUtilService.getBean(EntityManager.class);
    entityManager.persist(new OrderDetailHistory(target, action));
  }
}