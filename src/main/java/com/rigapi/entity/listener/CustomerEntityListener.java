package com.rigapi.entity.listener;

import com.rigapi.domain.Action;
import com.rigapi.entity.Customer;
import com.rigapi.entity.CustomerHistory;
import com.rigapi.service.BeanUtilService;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;

public class CustomerEntityListener {

  @PrePersist
  public void prePersist(Customer target) {
    perform(target, Action.INSERTED);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  void perform(Customer target, Action action) {
    EntityManager entityManager = BeanUtilService.getBean(EntityManager.class);
    entityManager.persist(new CustomerHistory(target, action));
  }
}