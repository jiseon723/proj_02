package com._2.proj_02.repository;

import com._2.proj_02.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    void deleteByCartIdAndUserId(Long cartId, Long userId);
    void deleteByCartIdInAndUserId(List<Long> cartIds, Long userId);
}
