package com.example.delivery.order.controller;

import com.example.delivery.order.dto.request.OrderRequestDto;
import com.example.delivery.order.dto.request.OrderStatusRequestDto;
import com.example.delivery.order.dto.response.OrderResponseDto;
import com.example.delivery.order.entity.Order;
import com.example.delivery.order.service.OrderService;
import com.example.delivery.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 - 고객
    @PostMapping("/orders")
    public OrderResponseDto createOrder(@Valid @RequestBody OrderRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(
                requestDto,
                userDetails.getUser()
        );
    }

    // 주문 조회 - 모두
    @GetMapping("/orders")
    public List<OrderResponseDto> getOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return orderService.getOrders(userDetails.getUser());
    }

    // 주문 취소 - 고객
    @DeleteMapping("/orders/{id}")
    public void cancelOrder(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.cancelOrder(id,userDetails.getUser());
    }

    // 주문 상태 변경 - 관리자
    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long id,
                                                  @Valid @RequestBody OrderStatusRequestDto request,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        orderService.changeOrderStatus(
                id,
                request,
                userDetails.getUser()
        );

        return ResponseEntity.noContent().build();
    }



}
