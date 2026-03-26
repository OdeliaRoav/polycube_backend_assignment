package com.polycube.payment.discount.entity;

import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.payment.entity.PaymentMethod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "discount_policies")
public class DiscountPolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String policyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DiscountCategory discountCategory;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MemberGrade targetGrade;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod targetPaymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    @Column(nullable = false)
    private int discountValue;

    @Column(nullable = false)
    private boolean active;

    public DiscountPolicyEntity(String policyName,
                                DiscountCategory discountCategory,
                                MemberGrade targetGrade,
                                PaymentMethod targetPaymentMethod,
                                DiscountType discountType,
                                int discountValue,
                                boolean active) {
        this.policyName = policyName;
        this.discountCategory = discountCategory;
        this.targetGrade = targetGrade;
        this.targetPaymentMethod = targetPaymentMethod;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.active = active;
    }

    public void updateDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public void deactivate() {
        this.active = false;
    }
}