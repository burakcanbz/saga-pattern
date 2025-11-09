package com.example.auth.repository;

import com.example.auth.entity.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepository  extends CrudRepository<Auth, Long> {
    Optional<Auth> findByEmail(String email);
    Optional<Auth> findByUsername(String username);
}
