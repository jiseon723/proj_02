package com._2.proj_02.domain.personal.repository;

import com._2.proj_02.domain.personal.entity.OrderItem;
import com._2.proj_02.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 주문 ID로 주문 상품 목록 조회
    List<OrderItem> findByOrder_OrderId(Long orderId);

    // 상품 ID로 주문 상품 목록 조회
    List<OrderItem> findByProduct(Product product);
}