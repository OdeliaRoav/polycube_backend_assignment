package com.polycube.payment.payment.service;

import com.polycube.payment.common.exception.BusinessException;
import com.polycube.payment.common.exception.ErrorCode;
import com.polycube.payment.common.exception.NotFoundException;
import com.polycube.payment.discount.service.DiscountService;
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
    private final DiscountService discountService;

    public PaymentService(OrderRepository orderRepository,
                          PaymentRepository paymentRepository,
                          DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.discountService = discountService;
    }

    @Transactional
    public PaymentResult pay(PaymentRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));

        int originalAmount = order.getOriginalPrice();
        int discountAmount = discountService.calculateDiscountAmount(order);
        int finalAmount = originalAmount - discountAmount;

        validateFinalAmount(finalAmount);

        Payment payment = new Payment(
                order,
                originalAmount,
                discountAmount,
                finalAmount,
                request.paymentMethod(),
                LocalDateTime.now()
        );

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResult.from(savedPayment);
    }

    private void validateFinalAmount(int finalAmount) {
        if (finalAmount < 0) {
            throw new BusinessException(ErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}