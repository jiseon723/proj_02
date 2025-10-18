package com._2.proj_02.repository;

import com._2.proj_02.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUserId(Long userId);
    List<Coupon> findByUserIdAndCouponStatus(Long userId, Boolean couponStatus);
    List<Coupon> findByUserIdAndCouponStatusAndExpiredAtAfter(Long userId, Boolean couponStatus, LocalDateTime expiredAt);
    List<Coupon> findByUserIdAndExpiredAtBefore(Long userId, LocalDateTime expiredAt);
}
