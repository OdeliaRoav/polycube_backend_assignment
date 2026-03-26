package com.polycube.payment.payment.dto;

import com.polycube.payment.payment.entity.Payment;
import com.polycube.payment.payment.entity.PaymentMethod;

import java.time.LocalDateTime;

public class PaymentResponse {

    private Long paymentId;
    private Long orderId;
    private String productName;
    private int originalAmount;
    private int discountAmount;
    private int finalAmount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;

    public PaymentResponse(
            Long paymentId,
            Long orderId,
            String productName,
            int originalAmount,
            int discountAmount,
            int finalAmount,
            PaymentMethod paymentMethod,
            LocalDateTime paidAt
    ) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.productName = productName;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
    }

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getOrder().getProductName(),
                payment.getOriginalAmount(),
                payment.getDiscountAmount(),
                payment.getFinalAmount(),
                payment.getPaymentMethod(),
                payment.getPaidAt()
        );
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}