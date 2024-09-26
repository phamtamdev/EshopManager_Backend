package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.PaymentDto;
import com.tambao.EshopManeger_Backend.entity.Payment;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.PaymentMapper;
import com.tambao.EshopManeger_Backend.repository.PaymentRepository;
import com.tambao.EshopManeger_Backend.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentDto getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new ResourceNotFoundException("Payment not found"));
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapToPayment(paymentDto);
        paymentRepository.save(payment);
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    public PaymentDto updatePayment(Integer paymentId, PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new ResourceNotFoundException("Payment not found"));
        payment.setId(paymentId);
        payment.setName(paymentDto.getName());
        payment.setImage(paymentDto.getImage());
        payment.setCode(paymentDto.getCode());
        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentMapper.mapToPaymentDto(updatedPayment);
    }

    @Override
    public void deletePaymentById(int Id) {
        Payment payment = paymentRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException("Payment not found"));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(PaymentMapper::mapToPaymentDto).collect(Collectors.toList());
    }

    @Override
    public Page<PaymentDto> getPaymentsWithPage(int page, int size) {
        Page<Payment> payments = paymentRepository.findAll(PageRequest.of(page, size));
        return payments.map(PaymentMapper::mapToPaymentDto);
    }

    @Override
    public Page<PaymentDto> getPaymentsWithPageAndSearch(int page, int size, String keyword) {
        Page<Payment> payments = paymentRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        return payments.map(PaymentMapper::mapToPaymentDto);
    }

    @Override
    public Page<PaymentDto> getPaymentsWithPageAndSorting(int page, int size, String field, String sortOrder) {
        Pageable pageable = createPageable(page,size ,field,sortOrder);
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(PaymentMapper::mapToPaymentDto);
    }

    @Override
    public Page<PaymentDto> getPaymentsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder) {
        Pageable pageable = createPageable(page,size ,field,sortOrder);
        Page<Payment> payments = paymentRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return payments.map(PaymentMapper::mapToPaymentDto);
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }
}
