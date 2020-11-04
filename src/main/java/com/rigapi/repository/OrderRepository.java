package com.rigapi.repository;

import com.rigapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {

  Page<Order> findByCustomerId(int id, Pageable pageable);

  Page<Order> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
