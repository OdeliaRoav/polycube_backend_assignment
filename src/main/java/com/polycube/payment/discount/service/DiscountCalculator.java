package com.polycube.payment.discount.service;

import com.polycube.payment.discount.dto.AppliedDiscountCommand;
import com.polycube.payment.discount.dto.DiscountCalculationResult;
import com.polycube.payment.discount.entity.DiscountPolicyEntity;
import com.polycube.payment.discount.entity.DiscountType;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.payment.entity.PaymentMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DiscountCalculator {

    private final DiscountPolicyReader discountPolicyReader;

    public DiscountCalculator(DiscountPolicyReader discountPolicyReader) {
        this.discountPolicyReader = discountPolicyReader;
    }

    public DiscountCalculationResult calculate(Order order, PaymentMethod paymentMethod) {
        int originalAmount = order.getOriginalPrice();
        int currentAmount = originalAmount;
        List<AppliedDiscountCommand> appliedDiscounts = new ArrayList<>();

        // 1. 회원 등급 할인
        var gradePolicyOptional = discountPolicyReader.getActiveGradeDiscountPolicy(order.getMember().getGrade());
        if (gradePolicyOptional.isPresent()) {
            DiscountPolicyEntity gradePolicy = gradePolicyOptional.get();
            int discountAmount = calculateDiscountAmount(currentAmount, gradePolicy);

            if (discountAmount > 0) {
                appliedDiscounts.add(new AppliedDiscountCommand(
                        gradePolicy.getDiscountCategory(),
                        gradePolicy.getPolicyName(),
                        order.getMember().getGrade(),
                        null,
                        gradePolicy.getDiscountType(),
                        gradePolicy.getDiscountValue(),
                        discountAmount
                ));
                currentAmount -= discountAmount;
            }
        }

        // 2. 결제수단 추가 할인
        var paymentMethodPolicyOptional = discountPolicyReader.getActivePaymentMethodDiscountPolicy(paymentMethod);
        if (paymentMethodPolicyOptional.isPresent()) {
            DiscountPolicyEntity paymentMethodPolicy = paymentMethodPolicyOptional.get();
            int discountAmount = calculateDiscountAmount(currentAmount, paymentMethodPolicy);

            if (discountAmount > 0) {
                appliedDiscounts.add(new AppliedDiscountCommand(
                        paymentMethodPolicy.getDiscountCategory(),
                        paymentMethodPolicy.getPolicyName(),
                        null,
                        paymentMethod,
                        paymentMethodPolicy.getDiscountType(),
                        paymentMethodPolicy.getDiscountValue(),
                        discountAmount
                ));
                currentAmount -= discountAmount;
            }
        }

        int totalDiscountAmount = appliedDiscounts.stream()
                .mapToInt(AppliedDiscountCommand::discountAmount)
                .sum();

        return new DiscountCalculationResult(
                originalAmount,
                appliedDiscounts,
                totalDiscountAmount,
                currentAmount
        );
    }

    private int calculateDiscountAmount(int baseAmount, DiscountPolicyEntity policy) {
        if (policy.getDiscountType() == DiscountType.FIXED) {
            return Math.min(baseAmount, policy.getDiscountValue());
        }

        return (int) (baseAmount * (policy.getDiscountValue() / 100.0));
    }
}