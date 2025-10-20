package com._2.proj_02.repository;

import com._2.proj_02.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByUserId(Long userId);
    void deleteByWishlistIdAndUserId(Long wishlistId, Long userId);
}
