package com.example.delivery.order.repository;

import com.example.delivery.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // CUSTOMER가 부르면 본인이 한 주문만 나옵니다.
    @EntityGraph(attributePaths = "menu")
    List<Order> findAllByUserId(Long id);
    // OWNER가 부르면 본인 메뉴에 들어온 주문만 나옵니다.
    @EntityGraph(attributePaths = "menu")
    List<Order> findAllByMenuUserId(Long id);
}



