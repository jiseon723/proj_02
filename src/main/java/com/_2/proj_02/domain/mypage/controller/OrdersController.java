package com._2.proj_02.domain.mypage.controller;

import com._2.proj_02.domain.mypage.dto.request.DeliveryRequest;
import com._2.proj_02.domain.mypage.dto.response.DeliveryResponse;
import com._2.proj_02.domain.mypage.dto.response.OrdersResponse;
import com._2.proj_02.domain.mypage.service.DeliveryService;
import com._2.proj_02.domain.mypage.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/mypage/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final DeliveryService deliveryService;

    // 주문 목록 페이지
    @GetMapping
    public String ordersList(@RequestParam(required = false) Long userId, Model model) {
        // TODO: 실제로는 세션에서 userId를 가져와야 함
        if (userId == null) {
            userId = 1L; // 테스트용 기본값
        }

        List<OrdersResponse> orders = ordersService.getOrdersByUserId(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId);

        return "mypage/orders";
    }

    // 주문 상세 조회 (AJAX)
    @GetMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<OrdersResponse> getOrderDetail(@PathVariable Long orderId) {
        OrdersResponse order = ordersService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }

    // 주문 삭제 (AJAX)
    @DeleteMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        ordersService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // 배송 정보 수정 (AJAX)
    @PatchMapping("/delivery")
    @ResponseBody
    public ResponseEntity<DeliveryResponse> updateDelivery(@RequestBody DeliveryRequest request) {
        DeliveryResponse response = deliveryService.updateDelivery(request);
        return ResponseEntity.ok(response);
    }
}
