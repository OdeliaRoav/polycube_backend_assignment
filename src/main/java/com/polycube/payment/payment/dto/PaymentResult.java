package com.polycube.payment.payment.dto;

import com.polycube.payment.payment.entity.Payment;
import com.polycube.payment.payment.entity.PaymentMethod;

import java.time.LocalDateTime;

public record PaymentResult(
        Long paymentId,
        String productName,
        int originalAmount,
        int discountAmount,
        int finalAmount,
        PaymentMethod paymentMethod,
        LocalDateTime paidAt
) {
    public static PaymentResult from(Payment payment) {
        return new PaymentResult(
                payment.getId(),
                payment.getOrder().getProductName(),
                payment.getOriginalAmount(),
                payment.getDiscountAmount(),
                payment.getFinalAmount(),
                payment.getPaymentMethod(),
                payment.getPaidAt()
        );
    }
}