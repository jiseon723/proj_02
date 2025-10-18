package com._2.proj_02.service;

import com._2.proj_02.dto.*;
import com._2.proj_02.entity.*;
import com._2.proj_02.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MyPageService {

    private final UserAddressRepository userAddressRepository;
    private final CartRepository cartRepository;
    private final FollowRepository followRepository;
    private final WishListRepository wishListRepository;
    private final CouponRepository couponRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    // 비밀번호 본인확인
    @Transactional
    public void verifyPassword(PasswordVerifyDto dto) {
        // TODO: 실제 비밀번호 확인 로직 구현
        // TODO: 인증 성공 시 Redis 캐시에 6시간 저장
        log.info("비밀번호 인증 시도");
    }

    // 기본 정보
    public ProfileResponseDto getProfile() {
        // TODO: 현재 로그인한 사용자 정보 조회
        Long userId = getCurrentUserId();

        return ProfileResponseDto.builder()
                .userId(userId)
                .nickname("테스트유저")
                .email("test@example.com")
                .phoneNumber("010-1234-5678")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public void updateProfile(ProfileUpdateDto dto) {
        Long userId = getCurrentUserId();
        // TODO: 프로필 업데이트 로직 구현
        // TODO: 전화번호/이메일 변경 시 인증번호 확인
        log.info("프로필 업데이트: userId={}", userId);
    }

    @Transactional
    public void sendPhoneVerification(PhoneDto dto) {
        // TODO: 전화번호 인증번호 발송 로직
        log.info("전화번호 인증번호 발송: {}", dto.getPhoneNumber());
    }

    @Transactional
    public void sendEmailVerification(EmailDto dto) {
        // TODO: 이메일 인증번호 발송 로직
        log.info("이메일 인증번호 발송: {}", dto.getEmail());
    }

    // 배송지 관리
    public List<AddressResponseDto> getAddressList() {
        Long userId = getCurrentUserId();
        List<UserAddress> addresses = userAddressRepository.findByUserId(userId);

        return addresses.stream()
                .map(this::convertToAddressResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAddress(AddressDto dto) {
        Long userId = getCurrentUserId();

        // 기본 주소로 설정 시 기존 기본 주소 해제
        if (dto.getIsDefault()) {
            updateDefaultAddress(userId);
        }

        UserAddress address = UserAddress.builder()
                .userId(userId)
                .recipientName(dto.getRecipientName())
                .baseAddress(dto.getBaseAddress())
                .detailAddress(dto.getDetailAddress())
                .zipcode(dto.getZipcode())
                .isDefault(dto.getIsDefault())
                .build();

        userAddressRepository.save(address);
    }

    @Transactional
    public void updateAddress(Long addressId, AddressDto dto) {
        Long userId = getCurrentUserId();
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

        if (!address.getUserId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        if (dto.getIsDefault() && !address.getIsDefault()) {
            updateDefaultAddress(userId);
        }

        address.setRecipientName(dto.getRecipientName());
        address.setBaseAddress(dto.getBaseAddress());
        address.setDetailAddress(dto.getDetailAddress());
        address.setZipcode(dto.getZipcode());
        address.setIsDefault(dto.getIsDefault());
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Long userId = getCurrentUserId();
        userAddressRepository.deleteByUserAddressIdAndUserId(addressId, userId);
    }

    // ========== 5. 장바구니 ==========
    public List<CartResponseDto> getCartList() {
        Long userId = getCurrentUserId();
        List<Cart> carts = cartRepository.findByUserId(userId);

        return carts.stream()
                .map(this::convertToCartResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCart(Long cartId, CartUpdateDto dto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));

        cart.setQuantity(dto.getQuantity());
    }

    @Transactional
    public void deleteCart(Long cartId) {
        Long userId = getCurrentUserId();
        cartRepository.deleteByCartIdAndUserId(cartId, userId);
    }

    @Transactional
    public void deleteSelectedCart(List<Long> cartIds) {
        Long userId = getCurrentUserId();
        cartRepository.deleteByCartIdInAndUserId(cartIds, userId);
    }

    // ========== 6. 배송조회 ==========
    public List<DeliveryResponseDto> getDeliveryList() {
        // TODO: 주문 엔티티와 연동하여 배송 정보 조회
        return List.of();
    }

    @Transactional
    public void cancelDelivery(Long orderId) {
        // TODO: 배송 취소 로직 (배송 준비중인 경우만)
        log.info("배송 취소 요청: orderId={}", orderId);
    }

    // ========== 7. 배송 상세 ==========
    public DeliveryDetailDto getDeliveryDetail(Long orderId) {
        // TODO: 주문 상세 정보 및 배송 추적 정보 조회
        return DeliveryDetailDto.builder()
                .orderId(orderId)
                .orderNumber("ORD-" + orderId)
                .build();
    }

    @Transactional
    public void updateDeliveryAddress(Long orderId, AddressDto dto) {
        // TODO: 배송지 수정 (배송 전 단계만 가능)
        log.info("배송지 수정: orderId={}", orderId);
    }

    @Transactional
    public void confirmReceipt(Long orderId) {
        // TODO: 수취 완료 처리
        log.info("수취 완료 처리: orderId={}", orderId);
    }

    // ========== 8. 주문내역 ==========
    public List<OrderResponseDto> getOrdersList() {
        // TODO: 주문 리스트 조회 (최신순 정렬)
        return List.of();
    }

    // ========== 9. 주문 상세 ==========
    public OrderDetailDto getOrderDetail(Long orderId) {
        // TODO: 주문 상세 정보 조회
        return OrderDetailDto.builder()
                .orderId(orderId)
                .orderNumber("ORD-" + orderId)
                .build();
    }

    @Transactional
    public void requestRefund(Long orderId, RefundDto dto) {
        // TODO: 환불 요청 처리
        log.info("환불 요청: orderId={}, reason={}", orderId, dto.getReason());
    }

    @Transactional
    public void requestExchange(Long orderId, ExchangeDto dto) {
        // TODO: 교환 요청 처리
        log.info("교환 요청: orderId={}, reason={}", orderId, dto.getReason());
    }

    // ========== 10. 팔로우 목록 ==========
    public List<FollowResponseDto> getFollowList(String category) {
        Long userId = getCurrentUserId();
        List<Follow> follows = followRepository.findByUserId(userId);

        // TODO: Seller 정보와 조인하여 DTO 생성
        // TODO: 카테고리 필터링
        return List.of();
    }

    @Transactional
    public void unfollowSeller(Long sellerId) {
        Long userId = getCurrentUserId();
        followRepository.deleteBySellerIdAndUserId(sellerId, userId);
    }

    // ========== 11. 위시리스트 ==========
    public List<WishListResponseDto> getWishlist(String category) {
        Long userId = getCurrentUserId();
        List<WishList> wishList = wishListRepository.findByUserId(userId);

        // TODO: Product 정보와 조인하여 DTO 생성
        // TODO: 카테고리 필터링
        return List.of();
    }

    @Transactional
    public void deleteWishlist(Long wishlistId) {
        Long userId = getCurrentUserId();
        wishListRepository.deleteByWishlistIdAndUserId(wishlistId, userId);
    }

    // ========== 12. 문의 내역 ==========
    public List<InquiryResponseDto> getInquiriesList() {
        // TODO: 문의 엔티티와 연동하여 조회 (최신순)
        return List.of();
    }

    // ========== 13. 리뷰 내역 ==========
    public List<ReviewResponseDto> getReviewsList() {
        // TODO: 리뷰 엔티티와 연동하여 조회
        return List.of();
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewDto dto) {
        // TODO: 리뷰 수정 로직
        log.info("리뷰 수정: reviewId={}", reviewId);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        // TODO: 리뷰 삭제 로직
        log.info("리뷰 삭제: reviewId={}", reviewId);
    }

    // ========== 14. 결제수단 ==========
    public List<PaymentMethodResponseDto> getPaymentMethodsList() {
        Long userId = getCurrentUserId();
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserId(userId);

        return paymentMethods.stream()
                .map(this::convertToPaymentMethodResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addPaymentMethod(PaymentMethodDto dto) {
        Long userId = getCurrentUserId();

        // TODO: 금융결제원 API 연동하여 결제수단 검증

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .userId(userId)
                .orderId(0L) // TODO: 실제 주문 ID 매핑 필요
                .paymentStatus("REGISTERED")
                .type(dto.getType())
                .bankName(dto.getBankName())
                .accountNumber(maskAccountNumber(dto.getAccountNumber()))
                .cardNumber(maskCardNumber(dto.getCardNumber()))
                .build();

        paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public void updatePaymentMethod(Long paymentId, PaymentMethodDto dto) {
        Long userId = getCurrentUserId();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("결제수단을 찾을 수 없습니다."));

        if (!paymentMethod.getUserId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        paymentMethod.setType(dto.getType());
        paymentMethod.setBankName(dto.getBankName());
        paymentMethod.setAccountNumber(maskAccountNumber(dto.getAccountNumber()));
        paymentMethod.setCardNumber(maskCardNumber(dto.getCardNumber()));
    }

    @Transactional
    public void deletePaymentMethod(Long paymentId) {
        Long userId = getCurrentUserId();
        paymentMethodRepository.deleteByPaymentIdAndUserId(paymentId, userId);
    }

    // ========== 15. 쿠폰 정보 ==========
    public List<CouponResponseDto> getCouponsList(String status) {
        Long userId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        List<Coupon> coupons;

        if ("available".equals(status)) {
            // 사용 가능한 쿠폰 (미사용 + 만료 안됨)
            coupons = couponRepository.findByUserIdAndCouponStatusAndExpiredAtAfter(userId, false, now);
        } else if ("used".equals(status)) {
            // 사용 완료 쿠폰
            coupons = couponRepository.findByUserIdAndCouponStatus(userId, true);
        } else if ("expired".equals(status)) {
            // 만료된 쿠폰
            coupons = couponRepository.findByUserIdAndExpiredAtBefore(userId, now);
        } else {
            // 전체 조회
            coupons = couponRepository.findByUserId(userId);
        }

        return coupons.stream()
                .map(this::convertToCouponResponseDto)
                .collect(Collectors.toList());
    }

    // ========== Private Helper Methods ==========

    private Long getCurrentUserId() {
        // TODO: Spring Security에서 현재 로그인한 사용자 ID 가져오기
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return 1L; // 임시 값
    }

    private void updateDefaultAddress(Long userId) {
        userAddressRepository.findByUserIdAndIsDefault(userId, true)
                .ifPresent(address -> address.setIsDefault(false));
    }

    private AddressResponseDto convertToAddressResponseDto(UserAddress address) {
        return AddressResponseDto.builder()
                .userAddressId(address.getUserAddressId())
                .recipientName(address.getRecipientName())
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .zipcode(address.getZipcode())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .build();
    }

    private CartResponseDto convertToCartResponseDto(Cart cart) {
        // TODO: Product 정보 조인하여 가격, 이름, 이미지 등 가져오기
        return CartResponseDto.builder()
                .cartId(cart.getCartId())
                .productId(cart.getProductId())
                .productName("상품명") // TODO: Product에서 가져오기
                .productImage("이미지URL") // TODO: Product에서 가져오기
                .quantity(cart.getQuantity())
                .price(null) // TODO: Product에서 가져오기
                .totalPrice(null) // TODO: 계산
                .inStock(true) // TODO: Product 재고 확인
                .createdAt(cart.getCreatedAt())
                .build();
    }

    private PaymentMethodResponseDto convertToPaymentMethodResponseDto(PaymentMethod pm) {
        return PaymentMethodResponseDto.builder()
                .paymentId(pm.getPaymentId())
                .type(pm.getType())
                .bankName(pm.getBankName())
                .maskedAccountNumber(pm.getAccountNumber())
                .maskedCardNumber(pm.getCardNumber())
                .createdAt(pm.getCreatedAt())
                .build();
    }

    private CouponResponseDto convertToCouponResponseDto(Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        boolean isExpired = coupon.getExpiredAt().isBefore(now);

        return CouponResponseDto.builder()
                .couponId(coupon.getCouponId())
                .couponCode(coupon.getCouponCode())
                .name(coupon.getName())
                .discountRate(coupon.getDiscountRate())
                .discountAmount(coupon.getDiscountAmount())
                .couponType(coupon.getCouponType())
                .discountCategory(coupon.getDiscountCategory())
                .couponStatus(coupon.getCouponStatus())
                .expiredAt(coupon.getExpiredAt())
                .isExpired(isExpired)
                .build();
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return accountNumber;
        }
        int length = accountNumber.length();
        return accountNumber.substring(0, length - 4).replaceAll("\\d", "*")
                + accountNumber.substring(length - 4);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }
        int length = cardNumber.length();
        return cardNumber.substring(0, 4) + "-****-****-" + cardNumber.substring(length - 4);
    }
}
