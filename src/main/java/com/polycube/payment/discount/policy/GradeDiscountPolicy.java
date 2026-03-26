package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class GradeDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscountAmount(Order order) {
        MemberGrade grade = order.getMember().getGrade();

        if (grade == MemberGrade.NORMAL) {
            return 0;
        }

        if (grade == MemberGrade.VIP) {
            return 1000;
        }

        if (grade == MemberGrade.VVIP) {
            return order.getOriginalPrice() / 10;
        }

        return 0;
    }
}