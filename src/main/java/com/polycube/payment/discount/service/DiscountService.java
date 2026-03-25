package com.polycube.payment.discount.service;

import com.polycube.payment.discount.policy.DiscountPolicy;
import com.polycube.payment.discount.policy.DiscountPolicyResolver;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountPolicyResolver discountPolicyResolver;

    public DiscountService(DiscountPolicyResolver discountPolicyResolver) {
        this.discountPolicyResolver = discountPolicyResolver;
    }

    public int calculateDiscountAmount(Order order) {
        DiscountPolicy discountPolicy = discountPolicyResolver.resolve(order.getMember().getGrade());
        return discountPolicy.calculateDiscountAmount(order);
    }
}