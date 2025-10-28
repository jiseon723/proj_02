package com._2.proj_02.domain.personal.controller;


import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.personal.dto.request.PaymentMethodRequest;
import com._2.proj_02.domain.personal.dto.response.PaymentMethodResponse;
import com._2.proj_02.domain.personal.service.PaymentMethodService;
import com._2.proj_02.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/mypage/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    // 결제수단 목록 페이지
    @GetMapping
    @Operation(summary = "결제수단 목록 페이지")
    public RsData<List<PaymentMethodResponse>> paymentMethodList(@RequestParam(required = false) SiteUser siteUser) {
        if (siteUser == null) {
            return RsData.of("400", "유저 정보가 없습니다.");
        }

        List<PaymentMethodResponse> paymentMethod = paymentMethodService.getPaymentMethodsByUserId(siteUser);

        return RsData.of("200", "결제수단 다건 조회 성공", paymentMethod);
    }

    // 결제수단 등록
    @PostMapping
    @ResponseBody
    @Operation(summary = "결제수단 등록")
    public RsData<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.createPaymentMethod(request);
        return RsData.of("200", "결제수단 등록 성공", response);
    }

    // 결제수단 수정
    @PatchMapping("/{paymentId}")
    @ResponseBody
    @Operation(summary = "결제수단 수정")
    public RsData<PaymentMethodResponse> updatePaymentMethod(@PathVariable Long paymentId, @RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.updatePaymentMethod(paymentId, request);
        return RsData.of("200", "수량 수정 성공", response);
    }

    // 결제수단 삭제
    @DeleteMapping("/{paymentId}")
    @ResponseBody
    @Operation(summary = "결제수단 삭제")
    public RsData<Void> deletePaymentMethod(@PathVariable Long paymentId) {
        paymentMethodService.deletePaymentMethod(paymentId);
        return RsData.of("200", "삭제 성공");
    }

    // 기본 결제수단 설정
    @PatchMapping("/{paymentId}/default")
    @ResponseBody
    @Operation(summary = "기본 결제수단 설정")
    public RsData<Void> setDefaultPaymentMethod(@PathVariable Long paymentId, @RequestParam SiteUser siteUser) {
        paymentMethodService.setDefaultPaymentMethod(paymentId, siteUser);
        return RsData.of("200", "기본 결제수단 설정 성공");
    }
}