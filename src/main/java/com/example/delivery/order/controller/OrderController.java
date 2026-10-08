package com.example.delivery.order.controller;

import com.example.delivery.order.dto.request.OrderRequestDto;
import com.example.delivery.order.dto.response.OrderResponseDto;
import com.example.delivery.order.entity.Order;
import com.example.delivery.order.service.OrderService;
import com.example.delivery.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 - 고객만
    @PostMapping("/orders")
    public OrderResponseDto createOrder(@Valid @RequestBody OrderRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(
                requestDto,
                userDetails.getUser()
        );
    }

    // 주문 조회
    @GetMapping("/orders")
    public List<OrderResponseDto> getOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return orderService.getOrders(userDetails.getUser());
    }


    @DeleteMapping("/orders/{id}")
    public void cancelOrder(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.cancelOrder(id,userDetails.getUser());
    }

}
