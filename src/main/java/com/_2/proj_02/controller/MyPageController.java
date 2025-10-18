package com._2.proj_02.controller;


import com._2.proj_02.dto.*;
import com._2.proj_02.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 비밀번호 본인확인
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPassword(@RequestBody PasswordVerifyDto dto) {
        // 비밀번호 확인 로직 (6시간 캐시 저장)
        myPageService.verifyPassword(dto);
        return ResponseEntity.ok().build();
    }

    // 기본 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile() {
        ProfileResponseDto profile = myPageService.getProfile();
        return ResponseEntity.ok(profile);
    }

    // 기본 정보 수정
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDto dto) {
        myPageService.updateProfile(dto);
        return ResponseEntity.ok().build();
    }

    // 전화번호 인증번호 발송
    @PostMapping("/profile/verify-phone")
    public ResponseEntity<?> sendPhoneVerification(@RequestBody PhoneDto dto) {
        myPageService.sendPhoneVerification(dto);
        return ResponseEntity.ok().build();
    }

    // 이메일 인증번호 발송
    @PostMapping("/profile/verify-email")
    public ResponseEntity<?> sendEmailVerification(@RequestBody EmailDto dto) {
        myPageService.sendEmailVerification(dto);
        return ResponseEntity.ok().build();
    }

    // 배송지 리스트 조회
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponseDto>> getAddressList() {
        List<AddressResponseDto> addresses = myPageService.getAddressList();
        return ResponseEntity.ok(addresses);
    }

    // 배송지 추가
    @PostMapping("/addresses")
    public ResponseEntity<?> addAddress(@RequestBody AddressDto dto) {
        myPageService.addAddress(dto);
        return ResponseEntity.ok().build();
    }

    // 배송지 수정
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody AddressDto dto) {
        myPageService.updateAddress(addressId, dto);
        return ResponseEntity.ok().build();
    }

    // 배송지 삭제
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        myPageService.deleteAddress(addressId);
        return ResponseEntity.ok().build();
    }

    // 장바구니 리스트 조회
    @GetMapping("/cart")
    public ResponseEntity<List<CartResponseDto>> getCartList() {
        List<CartResponseDto> cartList = myPageService.getCartList();
        return ResponseEntity.ok(cartList);
    }

    // 장바구니 수정
    @PutMapping("/cart/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @RequestBody CartUpdateDto dto) {
        myPageService.updateCart(cartId, dto);
        return ResponseEntity.ok().build();
    }

    // 장바구니 항목 삭제
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        myPageService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

    // 선택 항목 삭제
    @DeleteMapping("/cart/selected")
    public ResponseEntity<?> deleteSelectedCart(@RequestBody List<Long> cartIds) {
        myPageService.deleteSelectedCart(cartIds);
        return ResponseEntity.ok().build();
    }

    // 배송 리스트 조회
    @GetMapping("/deliveries")
    public ResponseEntity<List<DeliveryResponseDto>> getDeliveryList() {
        List<DeliveryResponseDto> deliveries = myPageService.getDeliveryList();
        return ResponseEntity.ok(deliveries);
    }

    // 배송 취소 요청
    @PostMapping("/deliveries/{orderId}/cancel")
    public ResponseEntity<?> cancelDelivery(@PathVariable Long orderId) {
        myPageService.cancelDelivery(orderId);
        return ResponseEntity.ok().build();
    }

    // 배송 상세 정보 조회
    @GetMapping("/deliveries/{orderId}")
    public ResponseEntity<DeliveryDetailDto> getDeliveryDetail(@PathVariable Long orderId) {
        DeliveryDetailDto detail = myPageService.getDeliveryDetail(orderId);
        return ResponseEntity.ok(detail);
    }

    // 배송지 수정
    @PutMapping("/deliveries/{orderId}/address")
    public ResponseEntity<?> updateDeliveryAddress(@PathVariable Long orderId, @RequestBody AddressDto dto) {
        myPageService.updateDeliveryAddress(orderId, dto);
        return ResponseEntity.ok().build();
    }

    // 수취 완료 처리
    @PostMapping("/deliveries/{orderId}/confirm")
    public ResponseEntity<?> confirmReceipt(@PathVariable Long orderId) {
        myPageService.confirmReceipt(orderId);
        return ResponseEntity.ok().build();
    }

    // 주문 리스트 조회
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrdersList() {
        List<OrderResponseDto> orders = myPageService.getOrdersList();
        return ResponseEntity.ok(orders);
    }

    // 주문 상세 정보 조회
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDetailDto> getOrderDetail(@PathVariable Long orderId) {
        OrderDetailDto detail = myPageService.getOrderDetail(orderId);
        return ResponseEntity.ok(detail);
    }

    // 환불 요청
    @PostMapping("/orders/{orderId}/refund")
    public ResponseEntity<?> requestRefund(@PathVariable Long orderId, @RequestBody RefundDto dto) {
        myPageService.requestRefund(orderId, dto);
        return ResponseEntity.ok().build();
    }

    // 교환 요청
    @PostMapping("/orders/{orderId}/exchange")
    public ResponseEntity<?> requestExchange(@PathVariable Long orderId, @RequestBody ExchangeDto dto) {
        myPageService.requestExchange(orderId, dto);
        return ResponseEntity.ok().build();
    }

    // 팔로우 공방 리스트 조회
    @GetMapping("/follows")
    public ResponseEntity<List<FollowResponseDto>> getFollowList(
            @RequestParam(required = false) String category) {
        List<FollowResponseDto> follows = myPageService.getFollowList(category);
        return ResponseEntity.ok(follows);
    }

    // 팔로우 취소
    @DeleteMapping("/follows/{sellerId}")
    public ResponseEntity<?> unfollowSeller(@PathVariable Long sellerId) {
        myPageService.unfollowSeller(sellerId);
        return ResponseEntity.ok().build();
    }

    // 위시리스트 조회
    @GetMapping("/wishlist")
    public ResponseEntity<List<WishListResponseDto>> getWishlist(
            @RequestParam(required = false) String category) {
        List<WishListResponseDto> wishlist = myPageService.getWishlist(category);
        return ResponseEntity.ok(wishlist);
    }

    // 위시리스트 항목 삭제
    @DeleteMapping("/wishlist/{wishlistId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable Long wishlistId) {
        myPageService.deleteWishlist(wishlistId);
        return ResponseEntity.ok().build();
    }

    // 문의 내역 리스트 조회
    @GetMapping("/inquiries")
    public ResponseEntity<List<InquiryResponseDto>> getInquiriesList() {
        List<InquiryResponseDto> inquiries = myPageService.getInquiriesList();
        return ResponseEntity.ok(inquiries);
    }

    // 리뷰 리스트 조회
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsList() {
        List<ReviewResponseDto> reviews = myPageService.getReviewsList();
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto dto) {
        myPageService.updateReview(reviewId, dto);
        return ResponseEntity.ok().build();
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        myPageService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    // 결제수단 리스트 조회
    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethodResponseDto>> getPaymentMethodsList() {
        List<PaymentMethodResponseDto> paymentMethods = myPageService.getPaymentMethodsList();
        return ResponseEntity.ok(paymentMethods);
    }

    // 결제수단 추가
    @PostMapping("/payment-methods")
    public ResponseEntity<?> addPaymentMethod(@RequestBody PaymentMethodDto dto) {
        myPageService.addPaymentMethod(dto);
        return ResponseEntity.ok().build();
    }

    // 결제수단 수정
    @PutMapping("/payment-methods/{paymentId}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Long paymentId, @RequestBody PaymentMethodDto dto) {
        myPageService.updatePaymentMethod(paymentId, dto);
        return ResponseEntity.ok().build();
    }

    // 결제수단 삭제
    @DeleteMapping("/payment-methods/{paymentId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long paymentId) {
        myPageService.deletePaymentMethod(paymentId);
        return ResponseEntity.ok().build();
    }

    // 쿠폰 리스트 조회
    @GetMapping("/coupons")
    public ResponseEntity<List<CouponResponseDto>> getCouponsList(
            @RequestParam(required = false) String status) {
        List<CouponResponseDto> coupons = myPageService.getCouponsList(status);
        return ResponseEntity.ok(coupons);
    }
}