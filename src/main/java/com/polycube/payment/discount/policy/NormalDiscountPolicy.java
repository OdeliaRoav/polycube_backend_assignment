package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class NormalDiscountPolicy implements DiscountPolicy {

    @Override
    public boolean supports(MemberGrade grade) {
        return grade == MemberGrade.NORMAL;
    }

    @Override
    public int calculateDiscountAmount(Order order) {
        return 0;
    }
}