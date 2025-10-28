package com._2.proj_02.domain.personal.service;

import com._2.proj_02.domain.auth.entity.SiteUser;
import com._2.proj_02.domain.personal.dto.request.PaymentMethodRequest;
import com._2.proj_02.domain.personal.dto.response.PaymentMethodResponse;
import com._2.proj_02.domain.personal.entity.PaymentMethod;
import com._2.proj_02.domain.personal.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    // 사용자별 결제수단 목록 조회
    public List<PaymentMethodResponse> getPaymentMethodsByUserId(SiteUser siteUser) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findBySiteUser(siteUser);

        return paymentMethods.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 결제수단 등록
    @Transactional
    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request) {
        // 기본 결제수단으로 설정하는 경우, 기존 기본 결제수단 해제
        if (request.getDefaultPayment() != null && request.getDefaultPayment()) {
            paymentMethodRepository.unsetDefaultBySiteUser(request.getSiteUser());
        }

        // 카드번호 마스킹 처리
        String maskedCardNumber = null;
        if (request.getCardNumber() != null && !request.getCardNumber().isEmpty()) {
            maskedCardNumber = maskCardNumber(request.getCardNumber());
        }

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .siteUser(SiteUser.builder().id(request.getSiteUser().getId()).build())
                .type(request.getType())
                .bankName(request.getBankName())
                .accountNumber(request.getAccountNumber())
                .cardCompany(request.getCardCompany())
                .cardNumber(maskedCardNumber)
                .defaultPayment(request.getDefaultPayment() != null ? request.getDefaultPayment() : false)
                .build();

        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
        return convertToResponse(saved);
    }

    // 결제수단 수정
    @Transactional
    public PaymentMethodResponse updatePaymentMethod(Long paymentId, PaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제수단을 찾을 수 없습니다."));

        // 기본 결제수단으로 변경하는 경우
        if (request.getDefaultPayment() != null && request.getDefaultPayment() && !paymentMethod.getDefaultPayment()) {
            paymentMethodRepository.unsetDefaultBySiteUser(paymentMethod.getSiteUser());
        }

        paymentMethod.setType(request.getType());
        paymentMethod.setBankName(request.getBankName());
        paymentMethod.setAccountNumber(request.getAccountNumber());
        paymentMethod.setCardCompany(request.getCardCompany());

        if (request.getCardNumber() != null && !request.getCardNumber().isEmpty()) {
            paymentMethod.setCardNumber(maskCardNumber(request.getCardNumber()));
        }

        if (request.getDefaultPayment() != null) {
            paymentMethod.setDefaultPayment(request.getDefaultPayment());
        }

        return convertToResponse(paymentMethod);
    }

    // 결제수단 삭제
    @Transactional
    public void deletePaymentMethod(Long paymentId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제수단을 찾을 수 없습니다."));

        paymentMethodRepository.delete(paymentMethod);
    }

    // 기본 결제수단 설정
    @Transactional
    public void setDefaultPaymentMethod(Long paymentId, SiteUser siteUser) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제수단을 찾을 수 없습니다."));

        // 기존 기본 결제수단 해제
        paymentMethodRepository.unsetDefaultBySiteUser(siteUser);

        // 새로운 기본 결제수단 설정
        paymentMethod.setDefaultPayment(true);
    }

    // 카드번호 마스킹 처리
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }

        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "****-****-****-" + lastFour;
    }

    // Entity -> Response DTO 변환
    private PaymentMethodResponse convertToResponse(PaymentMethod paymentMethod) {
        return PaymentMethodResponse.builder()
                .paymentId(paymentMethod.getPaymentId())
                .siteUser(paymentMethod.getSiteUser())
                .type(paymentMethod.getType())
                .bankName(paymentMethod.getBankName())
                .accountNumber(paymentMethod.getAccountNumber())
                .cardCompany(paymentMethod.getCardCompany())
                .cardNumber(paymentMethod.getCardNumber())
                .defaultPayment(paymentMethod.getDefaultPayment())
                .createdAt(paymentMethod.getCreatedAt())
                .modifiedDate(paymentMethod.getModifiedDate())
                .build();
    }
}