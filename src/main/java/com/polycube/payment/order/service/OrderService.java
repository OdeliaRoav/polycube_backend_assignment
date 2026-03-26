package com.polycube.payment.order.service;

import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.repository.MemberRepository;
import com.polycube.payment.order.dto.CreateOrderRequest;
import com.polycube.payment.order.dto.OrderResponse;
import com.polycube.payment.order.entity.Order;
import com.polycube.payment.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + request.getMemberId()));

        Order order = new Order(
                request.getProductName(),
                request.getOriginalPrice(),
                member
        );

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.from(savedOrder);
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다. id=" + orderId));

        return OrderResponse.from(order);
    }
}