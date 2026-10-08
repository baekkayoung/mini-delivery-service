package com.example.delivery.order.entity;

import com.example.delivery.menu.entity.Menu;
import com.example.delivery.order.dto.request.OrderRequestDto;
import com.example.delivery.order.dto.response.OrderResponseDto;
import com.example.delivery.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_id", nullable = false)
    private Menu menu;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "total_price",nullable = false)
    private int totalPrice;

    @Column(name = "delivery_address",nullable = false)
    private String deliveryAddress;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;


    public Order(
            User user,
            Menu menu,
            int quantity,
            String deliveryAddress,
            int totalPrice
    ) {
        this.user = user;
        this.menu = menu;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.REQUESTED;
    }
}
