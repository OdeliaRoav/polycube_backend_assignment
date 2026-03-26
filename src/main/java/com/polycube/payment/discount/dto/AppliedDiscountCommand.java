package com.polycube.payment.discount.dto;

import com.polycube.payment.discount.entity.DiscountCategory;
import com.polycube.payment.discount.entity.DiscountType;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.payment.entity.PaymentMethod;

public record AppliedDiscountCommand(
        DiscountCategory discountCategory,
        String policyName,
        MemberGrade appliedMemberGrade,
        PaymentMethod appliedPaymentMethod,
        DiscountType discountType,
        int discountValue,
        int discountAmount
) {
}