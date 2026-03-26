package com.polycube.payment.payment.service;

import com.polycube.payment.common.exception.BusinessException;
import com.polycube.payment.common.exception.ErrorCode;
import com.polycube.payment.common.exception.NotFoundException;
import com.polycube.payment.discount.dto.DiscountCalculationResult;
import com.polycube.payment.discount.service.DiscountCalculator;
import com.polycube.payment.discount.service.DiscountHistoryService;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.order.repository.OrderRepository;
import com.polycube.payment.payment.dto.PaymentRequest;
import com.polycube.payment.payment.dto.PaymentResult;
import com.polycube.payment.payment.entity.Payment;
import com.polycube.payment.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DiscountCalculator discountCalculator;
    private final DiscountHistoryService discountHistoryService;

    public PaymentService(OrderRepository orderRepository,
                          PaymentRepository paymentRepository,
                          DiscountCalculator discountCalculator,
                          DiscountHistoryService discountHistoryService) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.discountCalculator = discountCalculator;
        this.discountHistoryService = discountHistoryService;
    }

    @Transactional
    public PaymentResult pay(PaymentRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));

        DiscountCalculationResult calculationResult =
                discountCalculator.calculate(order, request.paymentMethod());

        validateFinalAmount(calculationResult.finalAmount());

        Payment payment = new Payment(
                order,
                calculationResult.originalAmount(),
                calculationResult.totalDiscountAmount(),
                calculationResult.finalAmount(),
                request.paymentMethod(),
                LocalDateTime.now()
        );

        Payment savedPayment = paymentRepository.save(payment);

        discountHistoryService.saveAll(savedPayment, calculationResult.appliedDiscounts());

        return PaymentResult.from(savedPayment);
    }

    private void validateFinalAmount(int finalAmount) {
        if (finalAmount < 0) {
            throw new BusinessException(ErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}