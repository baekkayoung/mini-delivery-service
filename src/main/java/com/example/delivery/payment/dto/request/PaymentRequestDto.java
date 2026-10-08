package com.example.delivery.payment.dto.request;

import com.example.delivery.payment.entity.PaymentOption;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequestDto {

    @NotNull(message = "결제 수단은 필수입니다.")
    private PaymentOption option;
}
