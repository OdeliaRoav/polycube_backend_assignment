package com.polycube.payment.payment.service;

import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.member.repository.MemberRepository;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.order.repository.OrderRepository;
import com.polycube.payment.payment.dto.PaymentRequest;
import com.polycube.payment.payment.dto.PaymentResult;
import com.polycube.payment.payment.entity.PaymentMethod;
import com.polycube.payment.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("NORMAL 회원은 할인 없이 결제된다")
    void payForNormalMember() {
        // given
        Member member = memberRepository.save(new Member("normal-user", MemberGrade.NORMAL));
        Order order = orderRepository.save(new Order("keyboard", 10_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.CREDIT_CARD);

        // when
        PaymentResult result = paymentService.pay(request);

        // then
        assertThat(result.originalAmount()).isEqualTo(10_000);
        assertThat(result.discountAmount()).isEqualTo(0);
        assertThat(result.finalAmount()).isEqualTo(10_000);
        assertThat(result.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
        assertThat(paymentRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("VIP 회원은 1000원 할인 후 결제된다")
    void payForVipMember() {
        // given
        Member member = memberRepository.save(new Member("vip-user", MemberGrade.VIP));
        Order order = orderRepository.save(new Order("monitor", 20_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.CREDIT_CARD);

        // when
        PaymentResult result = paymentService.pay(request);

        // then
        assertThat(result.originalAmount()).isEqualTo(20_000);
        assertThat(result.discountAmount()).isEqualTo(1_000);
        assertThat(result.finalAmount()).isEqualTo(19_000);
        assertThat(result.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    @DisplayName("VVIP 회원은 10퍼센트 할인 후 결제된다")
    void payForVvipMember() {
        // given
        Member member = memberRepository.save(new Member("vvip-user", MemberGrade.VVIP));
        Order order = orderRepository.save(new Order("laptop", 30_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.POINT);

        // when
        PaymentResult result = paymentService.pay(request);

        // then
        assertThat(result.originalAmount()).isEqualTo(30_000);
        assertThat(result.discountAmount()).isEqualTo(3_000);
        assertThat(result.finalAmount()).isEqualTo(27_000);
        assertThat(result.paymentMethod()).isEqualTo(PaymentMethod.POINT);
    }
}