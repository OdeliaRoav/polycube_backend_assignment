package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;

public interface DiscountPolicy {

    boolean supports(MemberGrade grade);

    int calculateDiscountAmount(Order order);
}