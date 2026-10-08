package com.example.delivery.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull(message = "메뉴를 선택해주세요")
    private Long menuId;

    @NotNull(message = "수량을 입력해주세요")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer  quantity;

    @NotBlank(message = "배달 주소를 입력해주세요")
    private String orderAddress;

 }
