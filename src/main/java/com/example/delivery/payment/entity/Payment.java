package com.example.delivery.payment.entity;

import com.example.delivery.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_sum", nullable = false)
    private int sum;

    @Column(name = "payment_option", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;

    public Payment(Order order, int sum, PaymentOption paymentOption) {
        this.order = order;
        this.sum = sum;
        this.paymentOption = paymentOption;
        this.paymentStatus = PaymentStatus.COMPLETED;
    }
}
