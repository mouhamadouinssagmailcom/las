package com.myvision.Super.Repository;

import com.myvision.Super.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    public Page<Order> findByNameContains(@Param(value = "mc") String mc, Pageable pageable);

}
