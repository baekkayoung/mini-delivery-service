package com.example.delivery.menu.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotBlank(message = "메뉴 이름은 필수입니다.")
    private String name;

    @Min(value = 1, message = "메뉴 가격은 1원 이상이어야 합니다.")
    private Integer  price;

    private String description;
}
