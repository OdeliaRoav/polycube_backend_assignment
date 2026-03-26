package com.polycube.payment.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Order", description = "주문 관련 API")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Operation(summary = "주문 생성", description = "테스트용 주문 생성 API")
    @PostMapping
    public Map<String, Object> createOrder(
            @RequestParam String itemName,
            @RequestParam int originalPrice,
            @RequestParam Long memberId
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "주문 생성 성공");
        response.put("itemName", itemName);
        response.put("originalPrice", originalPrice);
        response.put("memberId", memberId);
        return response;
    }

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 주문 정보를 조회하는 테스트용 API")
    @GetMapping("/{orderId}")
    public Map<String, Object> getOrder(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderId);
        response.put("itemName", "keyboard");
        response.put("originalPrice", 10000);
        response.put("memberId", 1L);
        return response;
    }

    @Operation(summary = "주문 목록 조회", description = "테스트용 주문 목록 조회 API")
    @GetMapping
    public Map<String, Object> getOrders() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "주문 목록 조회 성공");
        response.put("count", 3);
        return response;
    }
}