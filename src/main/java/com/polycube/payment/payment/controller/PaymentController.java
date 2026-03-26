package com.polycube.payment.payment.controller;

import com.polycube.payment.payment.dto.CreatePaymentRequest;
import com.polycube.payment.payment.dto.PaymentResponse;
import com.polycube.payment.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "결제 관련 API")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "결제 실행", description = "주문에 대해 실제 결제를 수행합니다.")
    @PostMapping
    public PaymentResponse pay(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.pay(request);
    }

    @Operation(summary = "결제 단건 조회", description = "결제 ID로 실제 결제 정보를 조회합니다.")
    @GetMapping("/{paymentId}")
    public PaymentResponse getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}