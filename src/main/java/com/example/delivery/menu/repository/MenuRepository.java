package com.example.delivery.menu.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.delivery.menu.dto.response.MenuResponseDto;
import com.example.delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByIsDeletedFalse();

    Optional<Menu> findByIdAndIsDeletedFalse(Long id);
}
