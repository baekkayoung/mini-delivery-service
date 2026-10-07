package com.example.delivery.order.entity;

import jakarta.persistence.Column;

import java.util.Date;

public class Order {



    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;
}
