package com._2.proj_02.repository;

import com._2.proj_02.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByUserId(Long userId);
    void deleteBySellerIdAndUserId(Long sellerId, Long userId);
}
