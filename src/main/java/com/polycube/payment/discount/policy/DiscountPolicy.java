package com.polycube.payment.discount.policy;

import com.polycube.payment.order.entity.Order;

public interface DiscountPolicy {
    int calculateDiscountAmount(Order order);
}