package com.example.delivery.order.entity;

public enum OrderStatus {
    REQUESTED, // 주문 요청 (결제전) - 바로 배달완료, 주문 수락 X
    PAID, // 결제 완료 (주문 확인) - 바로 배달완료 X, 주문 최소 X
    ACCEPTED, // 주문 수락 - 바로 주문 취소X
    DELIVERED, // 배달 완료 - 주문 취소 X
    CANCELLED // 주문 취소 - 다른 상태로 변이 X
}
