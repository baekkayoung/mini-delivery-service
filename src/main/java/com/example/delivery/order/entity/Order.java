package com.example.delivery.order.entity;

import com.example.delivery.menu.entity.Menu;
import com.example.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    public void cancel() {
        if (this.orderStatus != OrderStatus.REQUESTED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "주문요청 상태에서만 취소할 수 있습니다."
            );
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    // 상태 변경 규칙
    public void changeStatus(OrderStatus newStatus) {

        if (this.orderStatus == OrderStatus.PAID
                && newStatus == OrderStatus.ACCEPTED) {

            this.orderStatus = OrderStatus.ACCEPTED;
            return;
        }

        if (this.orderStatus == OrderStatus.ACCEPTED
                && newStatus == OrderStatus.DELIVERED) {

            this.orderStatus = OrderStatus.DELIVERED;
            return;
        }

        throw new IllegalStateException(
                "변경할 수 없는 주문 상태입니다."
        );
    }

    public void payment() {

        if (this.orderStatus != OrderStatus.REQUESTED) {
            throw new IllegalStateException(
                    "주문 요청 상태에서만 결제할 수 있습니다."
            );
        }

        this.orderStatus = OrderStatus.PAID;
    }
}
