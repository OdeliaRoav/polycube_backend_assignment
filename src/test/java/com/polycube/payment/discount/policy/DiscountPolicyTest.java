package com.polycube.payment.discount.policy;

import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.order.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @Test
    @DisplayName("NORMAL 회원은 할인이 적용되지 않는다")
    void normalMemberDiscount() {
        // given
        Member member = new Member("normal-user", MemberGrade.NORMAL);
        Order order = new Order("keyboard", 10_000, member);
        DiscountPolicy policy = new NormalDiscountPolicy();

        // when
        int discountAmount = policy.calculateDiscountAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(0);
    }

    @Test
    @DisplayName("VIP 회원은 1000원 고정 할인이 적용된다")
    void vipMemberDiscount() {
        // given
        Member member = new Member("vip-user", MemberGrade.VIP);
        Order order = new Order("keyboard", 10_000, member);
        DiscountPolicy policy = new VipDiscountPolicy();

        // when
        int discountAmount = policy.calculateDiscountAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(1_000);
    }

    @Test
    @DisplayName("VVIP 회원은 주문 금액의 10퍼센트 할인이 적용된다")
    void vvipMemberDiscount() {
        // given
        Member member = new Member("vvip-user", MemberGrade.VVIP);
        Order order = new Order("keyboard", 10_000, member);
        DiscountPolicy policy = new VvipDiscountPolicy();

        // when
        int discountAmount = policy.calculateDiscountAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(1_000);
    }

    @Test
    @DisplayName("VIP 할인은 주문 금액보다 클 수 없다")
    void vipDiscountShouldNotExceedOrderPrice() {
        // given
        Member member = new Member("vip-user", MemberGrade.VIP);
        Order order = new Order("pen", 500, member);
        DiscountPolicy policy = new VipDiscountPolicy();

        // when
        int discountAmount = policy.calculateDiscountAmount(order);

        // then
        assertThat(discountAmount).isEqualTo(500);
    }
}