package com.example.delivery.order.dto.response;

import com.example.delivery.order.entity.Order;
import com.example.delivery.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long orderId;
    private Long menuId;
    private String menuName;
    private int quantity;
    private int totalPrice;
    private String orderAddress;
    private OrderStatus orderStatus;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.menuId = order.getMenu().getId();
        this.menuName = order.getMenu().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderAddress = order.getDeliveryAddress();
        this.orderStatus = order.getOrderStatus();
    }
}
