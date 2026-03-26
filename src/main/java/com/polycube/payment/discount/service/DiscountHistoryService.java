package com.polycube.payment.discount.service;

import com.polycube.payment.discount.dto.AppliedDiscountCommand;
import com.polycube.payment.payment.entity.AppliedDiscountHistory;
import com.polycube.payment.payment.entity.Payment;
import com.polycube.payment.payment.repository.AppliedDiscountHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscountHistoryService {

    private final AppliedDiscountHistoryRepository appliedDiscountHistoryRepository;

    public DiscountHistoryService(AppliedDiscountHistoryRepository appliedDiscountHistoryRepository) {
        this.appliedDiscountHistoryRepository = appliedDiscountHistoryRepository;
    }

    @Transactional
    public void saveAll(Payment payment, List<AppliedDiscountCommand> commands) {
        List<AppliedDiscountHistory> histories = commands.stream()
                .map(command -> new AppliedDiscountHistory(
                        payment,
                        command.discountCategory(),
                        command.policyName(),
                        command.appliedMemberGrade(),
                        command.appliedPaymentMethod(),
                        command.discountType(),
                        command.discountValue(),
                        command.discountAmount()
                ))
                .toList();

        appliedDiscountHistoryRepository.saveAll(histories);
    }
}