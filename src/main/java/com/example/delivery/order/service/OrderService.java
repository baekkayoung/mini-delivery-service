package com.example.delivery.order.service;

import com.example.delivery.menu.entity.Menu;
import com.example.delivery.menu.repository.MenuRepository;
import com.example.delivery.order.dto.request.OrderRequestDto;
import com.example.delivery.order.dto.request.OrderStatusRequestDto;
import com.example.delivery.order.dto.response.OrderResponseDto;
import com.example.delivery.order.entity.Order;
import com.example.delivery.order.repository.OrderRepository;
import com.example.delivery.user.entity.User;
import com.example.delivery.user.entity.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


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

    public List<OrderResponseDto> getOrders(User user) {

        List<Order> orders;

        if (user.getRole() == UserRole.CUSTOMER) {
            orders = orderRepository.findAllByUserId(user.getId());

        } else {
            orders = orderRepository.findAllByMenuUserId(user.getId());
        }

        return orders.stream()
                .map(OrderResponseDto::new)
                .toList();

    }

    @Transactional
    public void cancelOrder(Long id, User user) {
        // 주문 조회
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"주문을 찾을 수 없습니다."));

        //  본인의 주문인지 확인
        if(!order.getUser().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"본인의 주문만 취소할 수 있습니다.");
        }

        // 주문 상태 취소로 변경
        order.cancel();
    }

    @Transactional
    public void changeOrderStatus(Long id, OrderStatusRequestDto request, User user) {

        // 주문 존재 확인
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "주문을 찾을 수 없습니다."
                ));

        // 본인 메뉴의 주문인지 확인
        if (!order.getMenu().getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "본인 메뉴의 주문만 변경할 수 있습니다."
            );
        }

        // 허용된 상태 전이인지 Order에서 검사
        order.changeStatus(request.getOrderStatus());
    }
}
