package com.polycube.payment.discount.dto;

import java.util.List;

public record DiscountCalculationResult(
        int originalAmount,
        List<AppliedDiscountCommand> appliedDiscounts,
        int totalDiscountAmount,
        int finalAmount
) {
}