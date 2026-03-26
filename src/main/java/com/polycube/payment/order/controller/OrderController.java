package com.polycube.payment.order.controller;

import com.polycube.payment.order.dto.CreateOrderRequest;
import com.polycube.payment.order.dto.OrderResponse;
import com.polycube.payment.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "주문 관련 API")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "주문 생성", description = "주문을 실제로 생성합니다.")
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 실제 주문 정보를 조회합니다.")
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }
}