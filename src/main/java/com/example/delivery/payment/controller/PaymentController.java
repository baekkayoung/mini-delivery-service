package com.example.delivery.payment.controller;

import com.example.delivery.payment.dto.request.PaymentRequestDto;
import com.example.delivery.payment.service.PaymentService;
import com.example.delivery.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;


    @PatchMapping("/orders/{orderId}/payments")
    public ResponseEntity<Void> payment(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        paymentService.payment(
                orderId,
                request,
                userDetails.getUser()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
