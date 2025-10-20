package com._2.proj_02.service;

import com._2.proj_02.dto.request.PaymentMethodCreateDTO;
import com._2.proj_02.dto.request.PaymentMethodUpdateDTO;
import com._2.proj_02.dto.response.PaymentMethodResponseDTO;
import com._2.proj_02.entity.PaymentMethod;
import com._2.proj_02.repository.CouponRepository;
import com._2.proj_02.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentMethodRepository paymentRepository;
    private final CouponRepository couponRepository;

    public List<PaymentMethodResponseDTO> getPaymentMethodsList() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMethodResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void addPaymentMethod(PaymentMethodCreateDTO dto) {
        PaymentMethod method = new PaymentMethod(dto);
        paymentRepository.save(method);
    }

    public void updatePaymentMethod(Long paymentId, PaymentMethodUpdateDTO dto) {
        PaymentMethod method = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("결제 수단 없음"));
        method.updateFromDTO(dto);
        paymentRepository.save(method);
    }

    public void deletePaymentMethod(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public List<CouponResponseDTO> getCouponsList(String status) {
        return couponRepository.findByStatus(status)
                .stream()
                .map(CouponResponseDTO::new)
                .collect(Collectors.toList());
    }
}