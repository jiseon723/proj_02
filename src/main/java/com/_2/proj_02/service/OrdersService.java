package com._2.proj_02.service;

import com._2.proj_02.dto.response.OrdersResponse;
import com._2.proj_02.entity.Delivery;
import com._2.proj_02.entity.OrderItem;
import com._2.proj_02.entity.Orders;
import com._2.proj_02.repository.DeliveryRepository;
import com._2.proj_02.repository.OrderItemRepository;
import com._2.proj_02.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;

    // 사용자별 주문 목록 조회
    public List<OrdersResponse> getOrdersByUserId(Long userId) {
        List<Orders> orders = ordersRepository.findByUserIdWithDelivery(userId);

        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 주문 상세 조회
    public OrdersResponse getOrderDetail(Long orderId) {
        Orders order = ordersRepository.findByIdWithDeliveryAndAddress(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        return convertToResponse(order);
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        ordersRepository.delete(order);
    }

    // Entity -> Response DTO 변환
    private OrdersResponse convertToResponse(Orders order) {
        // 주문 상품 목록 조회
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(order.getOrderId());

        List<OrdersResponse.OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(item -> OrdersResponse.OrderItemResponse.builder()
                        .orderItemId(item.getOrderItemId())
                        .orderId(item.getOrder().getOrderId())
                        .productId(item.getProductId())
                        .productName("상품명") // TODO: Product 엔티티에서 가져오기
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        // 배송 정보 변환
        OrdersResponse.DeliveryResponse deliveryResponse = null;
        if (order.getDelivery() != null) {
            Delivery delivery = order.getDelivery();

            OrdersResponse.UserAddressResponse addressResponse = null;
            if (delivery.getAddress() != null) {
                addressResponse = OrdersResponse.UserAddressResponse.builder()
                        .userAddressId(delivery.getAddress().getUserAddressId())
                        .recipientName(delivery.getAddress().getRecipientName())
                        .baseAddress(delivery.getAddress().getBaseAddress())
                        .detailAddress(delivery.getAddress().getDetailAddress())
                        .zipcode(delivery.getAddress().getZipcode())
                        .build();
            }

            deliveryResponse = OrdersResponse.DeliveryResponse.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .orderId(delivery.getOrder().getOrderId())
                    .trackingNumber(delivery.getTrackingNumber())
                    .deliveryStatus(delivery.getDeliveryStatus())
                    .completedAt(delivery.getCompletedAt())
                    .createdDate(delivery.getCreatedDate())
                    .modifiedDate(delivery.getModifiedDate())
                    .address(addressResponse)
                    .build();
        }

        return OrdersResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderCord(order.getOrderCord())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemResponses)
                .delivery(deliveryResponse)
                .build();
    }
}