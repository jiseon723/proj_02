package com._2.proj_02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodResponseDto {
    private Long paymentId;
    private String type;
    private String bankName;
    private String maskedAccountNumber;
    private String maskedCardNumber;
    private LocalDateTime createdAt;
}