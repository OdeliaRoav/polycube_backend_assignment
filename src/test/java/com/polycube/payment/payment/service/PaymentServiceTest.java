package com.polycube.payment.payment.service;

import com.polycube.payment.discount.entity.DiscountCategory;
import com.polycube.payment.discount.entity.DiscountPolicyEntity;
import com.polycube.payment.discount.entity.DiscountType;
import com.polycube.payment.discount.repository.DiscountPolicyRepository;
import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.member.repository.MemberRepository;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.order.repository.OrderRepository;
import com.polycube.payment.payment.dto.PaymentRequest;
import com.polycube.payment.payment.dto.PaymentResult;
import com.polycube.payment.payment.entity.AppliedDiscountHistory;
import com.polycube.payment.payment.entity.PaymentMethod;
import com.polycube.payment.payment.repository.AppliedDiscountHistoryRepository;
import com.polycube.payment.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DiscountPolicyRepository discountPolicyRepository;

    @Autowired
    private AppliedDiscountHistoryRepository appliedDiscountHistoryRepository;

    @Test
    @DisplayName("VIP 회원이 포인트로 결제하면 등급 할인 후 결제수단 5퍼센트 할인이 중복 적용된다")
    void payForVipMemberWithPointDiscount() {
        // given
        discountPolicyRepository.save(new DiscountPolicyEntity(
                "VIP_FIXED_DISCOUNT",
                DiscountCategory.GRADE,
                MemberGrade.VIP,
                null,
                DiscountType.FIXED,
                1_000,
                true
        ));

        discountPolicyRepository.save(new DiscountPolicyEntity(
                "POINT_EXTRA_DISCOUNT",
                DiscountCategory.PAYMENT_METHOD,
                null,
                PaymentMethod.POINT,
                DiscountType.RATE,
                5,
                true
        ));

        Member member = memberRepository.save(new Member("vip-user", MemberGrade.VIP));
        Order order = orderRepository.save(new Order("keyboard", 20_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.POINT);

        // when
        PaymentResult result = paymentService.pay(request);

        // then
        assertThat(result.originalAmount()).isEqualTo(20_000);
        assertThat(result.discountAmount()).isEqualTo(1_950);
        assertThat(result.finalAmount()).isEqualTo(18_050);

        List<AppliedDiscountHistory> histories = appliedDiscountHistoryRepository.findAll();
        assertThat(histories).hasSize(2);

        assertThat(histories)
                .extracting(AppliedDiscountHistory::getPolicyName)
                .containsExactlyInAnyOrder("VIP_FIXED_DISCOUNT", "POINT_EXTRA_DISCOUNT");

        assertThat(histories)
                .extracting(AppliedDiscountHistory::getDiscountAmount)
                .containsExactlyInAnyOrder(1_000, 950);
    }

    @Test
    @DisplayName("VVIP 회원이 포인트로 결제하면 등급 할인 후 결제수단 5퍼센트 할인이 순서대로 적용된다")
    void payForVvipMemberWithPointDiscount() {
        // given
        discountPolicyRepository.save(new DiscountPolicyEntity(
                "VVIP_RATE_DISCOUNT",
                DiscountCategory.GRADE,
                MemberGrade.VVIP,
                null,
                DiscountType.RATE,
                10,
                true
        ));

        discountPolicyRepository.save(new DiscountPolicyEntity(
                "POINT_EXTRA_DISCOUNT",
                DiscountCategory.PAYMENT_METHOD,
                null,
                PaymentMethod.POINT,
                DiscountType.RATE,
                5,
                true
        ));

        Member member = memberRepository.save(new Member("vvip-user", MemberGrade.VVIP));
        Order order = orderRepository.save(new Order("laptop", 30_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.POINT);

        // when
        PaymentResult result = paymentService.pay(request);

        // then
        assertThat(result.originalAmount()).isEqualTo(30_000);
        assertThat(result.discountAmount()).isEqualTo(4_350);
        assertThat(result.finalAmount()).isEqualTo(25_650);

        List<AppliedDiscountHistory> histories = appliedDiscountHistoryRepository.findAll();
        assertThat(histories).hasSize(2);

        assertThat(histories)
                .extracting(AppliedDiscountHistory::getPolicyName)
                .containsExactlyInAnyOrder("VVIP_RATE_DISCOUNT", "POINT_EXTRA_DISCOUNT");

        assertThat(histories)
                .extracting(AppliedDiscountHistory::getDiscountAmount)
                .containsExactlyInAnyOrder(3_000, 1_350);
    }

    @Test
    @DisplayName("정책이 수정되어도 과거 결제 이력은 결제 시점 기준으로 보존된다")
    void paymentHistoryShouldRemainAfterPolicyValueChange() {
        // given
        DiscountPolicyEntity vipPolicy = discountPolicyRepository.save(new DiscountPolicyEntity(
                "VIP_FIXED_DISCOUNT",
                DiscountCategory.GRADE,
                MemberGrade.VIP,
                null,
                DiscountType.FIXED,
                1_000,
                true
        ));

        Member member = memberRepository.save(new Member("vip-user", MemberGrade.VIP));
        Order order = orderRepository.save(new Order("monitor", 20_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.CREDIT_CARD);

        // when
        PaymentResult result = paymentService.pay(request);

        // 정책 변경
        vipPolicy.updateDiscountValue(2_000);

        // then
        assertThat(result.discountAmount()).isEqualTo(1_000);
        assertThat(result.finalAmount()).isEqualTo(19_000);

        List<AppliedDiscountHistory> histories = appliedDiscountHistoryRepository.findAll();
        assertThat(histories).hasSize(1);

        AppliedDiscountHistory history = histories.get(0);
        assertThat(history.getPolicyName()).isEqualTo("VIP_FIXED_DISCOUNT");
        assertThat(history.getDiscountValue()).isEqualTo(1_000);
        assertThat(history.getDiscountAmount()).isEqualTo(1_000);
    }

    @Test
    @DisplayName("정책이 비활성화되어도 과거 결제 이력은 그대로 보존된다")
    void paymentHistoryShouldRemainAfterPolicyDeactivation() {
        // given
        DiscountPolicyEntity pointPolicy = discountPolicyRepository.save(new DiscountPolicyEntity(
                "POINT_EXTRA_DISCOUNT",
                DiscountCategory.PAYMENT_METHOD,
                null,
                PaymentMethod.POINT,
                DiscountType.RATE,
                5,
                true
        ));

        Member member = memberRepository.save(new Member("normal-user", MemberGrade.NORMAL));
        Order order = orderRepository.save(new Order("mouse", 10_000, member));

        PaymentRequest request = new PaymentRequest(order.getId(), PaymentMethod.POINT);

        // when
        PaymentResult result = paymentService.pay(request);

        // 정책 비활성화
        pointPolicy.deactivate();

        // then
        assertThat(result.discountAmount()).isEqualTo(500);
        assertThat(result.finalAmount()).isEqualTo(9_500);

        List<AppliedDiscountHistory> histories = appliedDiscountHistoryRepository.findAll();
        assertThat(histories).hasSize(1);

        AppliedDiscountHistory history = histories.get(0);
        assertThat(history.getPolicyName()).isEqualTo("POINT_EXTRA_DISCOUNT");
        assertThat(history.getDiscountValue()).isEqualTo(5);
        assertThat(history.getDiscountAmount()).isEqualTo(500);
    }
}