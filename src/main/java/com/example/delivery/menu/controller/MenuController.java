package com.example.delivery.menu.controller;

import com.example.delivery.menu.dto.request.MenuRequestDto;
import com.example.delivery.menu.dto.response.MenuResponseDto;
import com.example.delivery.menu.entity.Menu;
import com.example.delivery.menu.service.MenuService;
import com.example.delivery.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menus")
    public MenuResponseDto createMenu(@Valid @RequestBody MenuRequestDto request,
                         @AuthenticationPrincipal UserDetails userDetails){
        return menuService.createMenu(request,userDetails);
    }

    @GetMapping("/menus")
    public List<MenuResponseDto> getAllMenus(){
        return menuService.findAllByIsDeletedFalse();
    }

    @GetMapping("/menus/{id}")
    public MenuResponseDto getMenu(@PathVariable Long id){
        return menuService.getMenu(id);
    }

    @PutMapping("/menus/{id}")
    public MenuResponseDto updateMenu(@PathVariable Long id,
                                      @Valid @RequestBody MenuRequestDto request,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        return menuService.updateMenu(id,request,userDetails.getUser());

    }
}
