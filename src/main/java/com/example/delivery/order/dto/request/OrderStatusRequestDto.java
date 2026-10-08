package com.example.delivery.order.dto.request;

import com.example.delivery.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {

    @NotNull(message = "변경할 주문 상태는 필수입니다.")
    private OrderStatus orderStatus;
}