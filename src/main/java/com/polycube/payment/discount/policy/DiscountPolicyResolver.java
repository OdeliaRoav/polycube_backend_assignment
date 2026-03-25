package com.polycube.payment.discount.policy;

import com.polycube.payment.common.exception.BusinessException;
import com.polycube.payment.common.exception.ErrorCode;
import com.polycube.payment.member.entity.MemberGrade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscountPolicyResolver {

    private final List<DiscountPolicy> discountPolicies;

    public DiscountPolicyResolver(List<DiscountPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies;
    }

    public DiscountPolicy resolve(MemberGrade grade) {
        return discountPolicies.stream()
                .filter(policy -> policy.supports(grade))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_POLICY_NOT_FOUND));
    }
}