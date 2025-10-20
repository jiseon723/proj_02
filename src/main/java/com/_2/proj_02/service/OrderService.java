package com._2.proj_02.service;

import com._2.proj_02.dto.request.DeliveryAddressUpdateDTO;
import com._2.proj_02.dto.request.ExchangeRequestDTO;
import com._2.proj_02.dto.request.RefundRequestDTO;
import com._2.proj_02.dto.response.DeliveryResponseDTO;
import com._2.proj_02.entity.Delivery;
import com._2.proj_02.repository.DeliveryRepository;
import com._2.proj_02.repository.OrderRepository;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    public List<DeliveryResponseDTO> getDeliveryList() {
        return deliveryRepository.findAll()
                .stream()
                .map(DeliveryResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void cancelDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 없음"));
        delivery.cancel();
        deliveryRepository.save(delivery);
    }

    public DeliveryResponseDTO getDeliveryDetail(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 없음"));
        return new DeliveryResponseDTO(delivery);
    }

    public void updateDeliveryAddress(DeliveryAddressUpdateDTO dto) {
        Delivery delivery = deliveryRepository.findById(dto.getDeliveryId())
                .orElseThrow(() -> new RuntimeException("배송 없음"));
        delivery.updateAddress(dto);
        deliveryRepository.save(delivery);
    }

    public void confirmReceipt(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("배송 없음"));
        delivery.confirmReceipt();
        deliveryRepository.save(delivery);
    }

    public List<OrderResponseDTO> getOrdersList() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        return new OrderResponseDTO(order);
    }

    public void requestRefund(Long orderId, RefundRequestDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        order.requestRefund(dto);
        orderRepository.save(order);
    }

    public void requestExchange(Long orderId, ExchangeRequestDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        order.requestExchange(dto);
        orderRepository.save(order);
    }
}