package com.example.delivery.payment.service;

import com.example.delivery.order.entity.Order;
import com.example.delivery.order.repository.OrderRepository;
import com.example.delivery.payment.dto.request.PaymentRequestDto;
import com.example.delivery.payment.entity.Payment;
import com.example.delivery.payment.entity.PaymentOption;
import com.example.delivery.payment.repository.PaymentRepository;
import com.example.delivery.user.entity.User;
import com.example.delivery.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void payment(
            Long orderId,
            PaymentRequestDto request,
            User user
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "주문을 찾을 수 없습니다."
                ));


        // 본인의 주문인지 확인
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "본인의 주문만 결제할 수 있습니다."
            );
        }

        // 결제 수단 확인
        if (request.getOption() != PaymentOption.CARD) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "카드 결제만 가능합니다."
            );
        }

        // 주문 상태 변경
        order.payment();

        // 주문 총액을 서버에서 가져옴
        Payment payment = new Payment(
                order,
                order.getTotalPrice(),
                request.getOption()
        );

        paymentRepository.save(payment);
    }




}
