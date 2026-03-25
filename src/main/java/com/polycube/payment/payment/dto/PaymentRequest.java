package com.polycube.payment.payment.dto;

import com.polycube.payment.payment.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull Long orderId,
        @NotNull PaymentMethod paymentMethod
) {
}