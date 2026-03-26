package com.polycube.payment.discount.service;

import com.polycube.payment.discount.policy.DiscountPolicy;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountPolicy discountPolicy;

    public DiscountService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public int calculateDiscountAmount(Order order) {
        return discountPolicy.calculateDiscountAmount(order);
    }
}