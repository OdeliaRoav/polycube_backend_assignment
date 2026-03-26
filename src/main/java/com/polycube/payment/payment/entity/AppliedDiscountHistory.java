package com.polycube.payment.payment.entity;

import com.polycube.payment.discount.entity.DiscountCategory;
import com.polycube.payment.discount.entity.DiscountType;
import com.polycube.payment.member.entity.MemberGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "applied_discount_histories")
public class AppliedDiscountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DiscountCategory discountCategory;

    @Column(nullable = false, length = 100)
    private String policyName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MemberGrade appliedMemberGrade;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod appliedPaymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    @Column(nullable = false)
    private int discountValue;

    @Column(nullable = false)
    private int discountAmount;

    public AppliedDiscountHistory(Payment payment,
                                  DiscountCategory discountCategory,
                                  String policyName,
                                  MemberGrade appliedMemberGrade,
                                  PaymentMethod appliedPaymentMethod,
                                  DiscountType discountType,
                                  int discountValue,
                                  int discountAmount) {
        this.payment = payment;
        this.discountCategory = discountCategory;
        this.policyName = policyName;
        this.appliedMemberGrade = appliedMemberGrade;
        this.appliedPaymentMethod = appliedPaymentMethod;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.discountAmount = discountAmount;
    }
}