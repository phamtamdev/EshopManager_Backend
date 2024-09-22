package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.BrandDto;
import com.tambao.EshopManeger_Backend.dto.PaymentDto;
import com.tambao.EshopManeger_Backend.service.Impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getALlPayments(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "sort-order", required = false) String sortOrder
    ) {
        if (page == null || size == null) {
            List<PaymentDto> payments = paymentService.getAllPayments();
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else if (keyword != null && field != null) {
            Page<PaymentDto> payments = paymentService.getPaymentsWithPageAndSearchAndSorting(page, size, keyword, field, sortOrder);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else if (keyword != null) {
            Page<PaymentDto> payments = paymentService.getPaymentsWithPageAndSearch(page, size, keyword);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else if (field != null) {
            Page<PaymentDto> payments = paymentService.getPaymentsWithPageAndSorting(page, size, field, sortOrder);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else {
            Page<PaymentDto> payments = paymentService.getPaymentsWithPage(page, size);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto) {
        paymentDto.setId(0);
        paymentDto = paymentService.savePayment(paymentDto);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer paymentId, @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.updatePayment(paymentId, paymentDto));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer paymentId) {
        paymentService.deletePaymentById(paymentId);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}
