package com.polycube.payment.discount.service;

import com.polycube.payment.discount.entity.DiscountCategory;
import com.polycube.payment.discount.entity.DiscountPolicyEntity;
import com.polycube.payment.discount.repository.DiscountPolicyRepository;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.payment.entity.PaymentMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DiscountPolicyReader {

    private final DiscountPolicyRepository discountPolicyRepository;

    public DiscountPolicyReader(DiscountPolicyRepository discountPolicyRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
    }

    public Optional<DiscountPolicyEntity> getActiveGradeDiscountPolicy(MemberGrade memberGrade) {
        return discountPolicyRepository.findByDiscountCategoryAndTargetGradeAndActiveTrue(
                DiscountCategory.GRADE,
                memberGrade
        );
    }

    public Optional<DiscountPolicyEntity> getActivePaymentMethodDiscountPolicy(PaymentMethod paymentMethod) {
        return discountPolicyRepository.findByDiscountCategoryAndTargetPaymentMethodAndActiveTrue(
                DiscountCategory.PAYMENT_METHOD,
                paymentMethod
        );
    }
}