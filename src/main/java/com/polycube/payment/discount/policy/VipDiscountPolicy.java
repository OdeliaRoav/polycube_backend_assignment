package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class VipDiscountPolicy implements DiscountPolicy {

    private static final int VIP_DISCOUNT_AMOUNT = 1_000;

    @Override
    public boolean supports(MemberGrade grade) {
        return grade == MemberGrade.VIP;
    }

    @Override
    public int calculateDiscountAmount(Order order) {
        return Math.min(order.getOriginalPrice(), VIP_DISCOUNT_AMOUNT);
    }
}