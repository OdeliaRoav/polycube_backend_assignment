package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class VvipDiscountPolicy implements DiscountPolicy {

    private static final double DISCOUNT_RATE = 0.10;

    @Override
    public boolean supports(MemberGrade grade) {
        return grade == MemberGrade.VVIP;
    }

    @Override
    public int calculateDiscountAmount(Order order) {
        return (int) (order.getOriginalPrice() * DISCOUNT_RATE);
    }
}