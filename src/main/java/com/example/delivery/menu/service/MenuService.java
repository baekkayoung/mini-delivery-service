package com.example.delivery.menu.service;

import com.example.delivery.menu.dto.request.MenuRequestDto;
import com.example.delivery.menu.dto.response.MenuResponseDto;
import com.example.delivery.menu.entity.Menu;
import com.example.delivery.menu.repository.MenuRepository;
import com.example.delivery.security.UserDetailsImpl;
import com.example.delivery.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDto createMenu(MenuRequestDto request, UserDetails userDetails) {
        // OWNER 권한 확인
        if (userDetails.getAuthorities().stream()
                .noneMatch(authority -> authority.getAuthority().equals("ROLE_OWNER"))) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "사장님만 메뉴를 등록할 수 있습니다."
            );
        }

        // 현재 로그인한 User 가져오기
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        User user = userDetailsImpl.getUser();

        // Menu 엔티티 생성
        Menu menu = new Menu(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                user
        );

        return new MenuResponseDto(menuRepository.save(menu));
    }

    public List<MenuResponseDto> findAllByIsDeletedFalse() {

        return menuRepository.findAllByIsDeletedFalse().stream().map(MenuResponseDto::new).toList();
    }

    public MenuResponseDto getMenu(Long id) {

        Menu menu = menuRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "메뉴를 찾을 수 없습니다."
                ));

        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long id, MenuRequestDto request, User user) {

        Menu menu = menuRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"메뉴를 찾을 수 없습니다."));

        if(!menu.getUser().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"본인의 메뉴만 수정할 수 있습니다.");
        }

        menu.update(
                request.getName(),
                request.getPrice(),
                request.getDescription()
        );

        return new MenuResponseDto(menu);

    }
}