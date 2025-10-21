package com._2.proj_02.domain.mypage.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersResponse {

    private Long orderId;
    private Long userId;
    private String orderCord;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;

    // 주문 상품 목록
    private List<OrderItemResponse> orderItems;

    // 배송 정보
    private DeliveryResponse delivery;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse {
        private Long orderItemId;
        private Long orderId;
        private Long productId;
        private String productName;
        private Long quantity;
        private BigDecimal price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DeliveryResponse {
        private Long deliveryId;
        private Long orderId;
        private String trackingNumber;
        private String deliveryStatus;
        private LocalDateTime completedAt;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private UserAddressResponse address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserAddressResponse {
        private Long userAddressId;
        private String recipientName;
        private String baseAddress;
        private String detailAddress;
        private String zipcode;
    }
}
