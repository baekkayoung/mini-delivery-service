package com.example.delivery.payment.entity;

import jakarta.persistence.Column;

import java.util.Date;

public class Payment {



    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;
}
