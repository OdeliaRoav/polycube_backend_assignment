package com.polycube.payment.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    private String productName;

    @Min(value = 0, message = "주문 금액은 0원 이상이어야 합니다.")
    private int originalPrice;

    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String productName, int originalPrice, Long memberId) {
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.memberId = memberId;
    }

    public String getProductName() {
        return productName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public Long getMemberId() {
        return memberId;
    }
}