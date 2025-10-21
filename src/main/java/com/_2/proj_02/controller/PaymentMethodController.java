package com._2.proj_02.controller;

import com._2.proj_02.dto.request.PaymentMethodRequest;
import com._2.proj_02.dto.response.PaymentMethodResponse;
import com._2.proj_02.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/mypage/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    // 결제수단 목록 페이지
    @GetMapping
    public String paymentMethodList(@RequestParam(required = false) Long userId, Model model) {
        // TODO: 실제로는 세션에서 userId를 가져와야 함
        if (userId == null) {
            userId = 1L; // 테스트용 기본값
        }

        List<PaymentMethodResponse> paymentMethods = paymentMethodService.getPaymentMethodsByUserId(userId);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("userId", userId);

        return "mypage/payment-methods";
    }

    // 결제수단 등록 (AJAX)
    @PostMapping
    @ResponseBody
    public ResponseEntity<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.createPaymentMethod(request);
        return ResponseEntity.ok(response);
    }

    // 결제수단 수정 (AJAX)
    @PatchMapping("/{paymentId}")
    @ResponseBody
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(
            @PathVariable Long paymentId,
            @RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.updatePaymentMethod(paymentId, request);
        return ResponseEntity.ok(response);
    }

    // 결제수단 삭제 (AJAX)
    @DeleteMapping("/{paymentId}")
    @ResponseBody
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long paymentId) {
        paymentMethodService.deletePaymentMethod(paymentId);
        return ResponseEntity.ok().build();
    }

    // 기본 결제수단 설정 (AJAX)
    @PatchMapping("/{paymentId}/default")
    @ResponseBody
    public ResponseEntity<Void> setDefaultPaymentMethod(
            @PathVariable Long paymentId,
            @RequestParam Long userId) {
        paymentMethodService.setDefaultPaymentMethod(paymentId, userId);
        return ResponseEntity.ok().build();
    }
}