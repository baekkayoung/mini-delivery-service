package com.example.delivery.order.service;

import com.example.delivery.menu.entity.Menu;
import com.example.delivery.menu.repository.MenuRepository;
import com.example.delivery.order.dto.request.OrderRequestDto;
import com.example.delivery.order.dto.response.OrderResponseDto;
import com.example.delivery.order.entity.Order;
import com.example.delivery.order.repository.OrderRepository;
import com.example.delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {

        // 메뉴 검증
        Menu menu = menuRepository.findByIdAndIsDeletedFalse(requestDto.getMenuId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "메뉴를 찾을 수 없습니다."
                ));

        // 총액 계산
        int totalPrice = menu.getPrice() * requestDto.getQuantity();

        Order order = new Order(
                user,
                menu,
                requestDto.getQuantity(),
                requestDto.getOrderAddress(),
                totalPrice
        );

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }
}
