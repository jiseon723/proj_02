package com._2.proj_02.repository;

import com._2.proj_02.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUserId(Long userId);
    void deleteByPaymentIdAndUserId(Long paymentId, Long userId);
}
