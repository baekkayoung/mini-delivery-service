package com.example.delivery.user.repository;

import com.example.delivery.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(@NotBlank @Size(min = 4, max = 20) String username);
    Optional<User> findByUsername(String username);
}
