package com.polycube.payment.payment.repository;

import com.polycube.payment.payment.entity.AppliedDiscountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppliedDiscountHistoryRepository extends JpaRepository<AppliedDiscountHistory, Long> {
}