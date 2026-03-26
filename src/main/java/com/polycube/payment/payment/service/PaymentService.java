package com.polycube.payment.payment.service;

import com.polycube.payment.discount.policy.DiscountPolicy;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.order.repository.OrderRepository;
import com.polycube.payment.payment.dto.CreatePaymentRequest;
import com.polycube.payment.payment.dto.PaymentResponse;
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
    private final DiscountPolicy discountPolicy;

    public PaymentService(OrderRepository orderRepository,
                          PaymentRepository paymentRepository,
                          DiscountPolicy discountPolicy) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.discountPolicy = discountPolicy;
    }

    @Transactional
    public PaymentResponse pay(CreatePaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다. id=" + request.getOrderId()));

        int originalAmount = order.getOriginalPrice();
        int discountAmount = discountPolicy.calculateDiscountAmount(order);
        int finalAmount = originalAmount - discountAmount;

        if (finalAmount < 0) {
            throw new IllegalStateException("최종 결제 금액은 0원 이상이어야 합니다.");
        }

        Payment payment = new Payment(
                order,
                originalAmount,
                discountAmount,
                finalAmount,
                request.getPaymentMethod(),
                LocalDateTime.now()
        );

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponse.from(savedPayment);
    }

    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제입니다. id=" + paymentId));

        return PaymentResponse.from(payment);
    }
}