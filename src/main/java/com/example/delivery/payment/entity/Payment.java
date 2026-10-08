package com.example.delivery.payment.entity;

import com.example.delivery.global.entity.BaseEntity;
import com.example.delivery.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity {

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


    public Payment(Order order, int sum, PaymentOption paymentOption) {
        this.order = order;
        this.sum = sum;
        this.paymentOption = paymentOption;
        this.paymentStatus = PaymentStatus.COMPLETED;
    }
}
