package com.polycube.payment.discount.repository;

import com.polycube.payment.discount.entity.DiscountCategory;
import com.polycube.payment.discount.entity.DiscountPolicyEntity;
import com.polycube.payment.member.entity.MemberGrade;
import com.polycube.payment.payment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicyEntity, Long> {

    Optional<DiscountPolicyEntity> findByDiscountCategoryAndTargetGradeAndActiveTrue(
            DiscountCategory discountCategory,
            MemberGrade targetGrade
    );

    Optional<DiscountPolicyEntity> findByDiscountCategoryAndTargetPaymentMethodAndActiveTrue(
            DiscountCategory discountCategory,
            PaymentMethod targetPaymentMethod
    );
}