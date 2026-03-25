package com.polycube.payment.payment.entity;

import com.polycube.payment.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    @Column(nullable = false)
    private int originalAmount;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    private int finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    public Payment(Order order, int originalAmount, int discountAmount, int finalAmount,
                   PaymentMethod paymentMethod, LocalDateTime paidAt) {
        this.order = order;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
    }
}