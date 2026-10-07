package com.example.delivery.menu.dto.response;

import com.example.delivery.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private String description;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }
}
