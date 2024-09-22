package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Page<Payment> findByNameContainingIgnoreCase(String key, Pageable pageable);
}
