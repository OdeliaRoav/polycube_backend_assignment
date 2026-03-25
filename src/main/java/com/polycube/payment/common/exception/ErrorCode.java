package com.polycube.payment.common.exception;

public enum ErrorCode {

    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "주문을 찾을 수 없습니다."),
    DISCOUNT_POLICY_NOT_FOUND("DISCOUNT_POLICY_NOT_FOUND", "회원 등급에 맞는 할인 정책을 찾을 수 없습니다."),
    INVALID_PAYMENT_AMOUNT("INVALID_PAYMENT_AMOUNT", "유효하지 않은 결제 금액입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}