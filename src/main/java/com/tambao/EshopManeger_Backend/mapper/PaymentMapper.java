package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.PaymentDto;
import com.tambao.EshopManeger_Backend.entity.Payment;

public class PaymentMapper {
    public static PaymentDto mapToPaymentDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getName(),
                payment.getImage(),
                payment.getCode()
        );
    }

    public static Payment mapToPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setName(paymentDto.getName());
        payment.setImage(paymentDto.getImage());
        payment.setCode(paymentDto.getCode());
        return payment;
    }
}
