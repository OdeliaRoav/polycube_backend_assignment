package com.polycube.payment.order.dto;

import com.polycube.payment.order.entity.Order;

public class OrderResponse {

    private Long orderId;
    private String productName;
    private int originalPrice;
    private Long memberId;
    private String memberName;

    public OrderResponse(Long orderId, String productName, int originalPrice, Long memberId, String memberName) {
        this.orderId = orderId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getOriginalPrice(),
                order.getMember().getId(),
                order.getMember().getName()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }
}