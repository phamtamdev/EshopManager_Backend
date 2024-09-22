package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.PaymentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPaymentService {
    PaymentDto getPaymentById(Integer paymentId);
    PaymentDto savePayment(PaymentDto payment);
    PaymentDto updatePayment(Integer paymentId, PaymentDto payment);
    void deletePaymentById(int Id);
    List<PaymentDto> getAllPayments();
    Page<PaymentDto> getPaymentsWithPage(int page, int size);
    Page<PaymentDto> getPaymentsWithPageAndSearch(int page, int size, String keyword);
    Page<PaymentDto> getPaymentsWithPageAndSorting(int page, int size, String field, String sortOrder);
    Page<PaymentDto> getPaymentsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder);
}
