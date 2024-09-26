package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Users findByEmail(String email);
    Users findByUserName(String username);
    Page<Users> findByFullNameContainingIgnoreCase(String keyword, Pageable pageable);
    Users findByPhone(String phone);
}
