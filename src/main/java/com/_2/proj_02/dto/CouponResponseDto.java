package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponseDto {
    private Long couponId;
    private String couponCode;
    private String name;
    private Long discountRate;
    private BigDecimal discountAmount;
    private String couponType;        // PERCENT, FIXED 등
    private String discountCategory;  // PRODUCT, DELIVERY 등
    private Boolean couponStatus;     // true: 사용완료
    private LocalDateTime expiredAt;
    private Boolean isExpired;
}