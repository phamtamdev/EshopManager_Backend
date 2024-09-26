package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.OrderItemDto;
import com.tambao.EshopManeger_Backend.service.Impl.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderItem(@PathVariable Integer orderId){
        return ResponseEntity.ok(orderItemService.getByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<?> addOrderItem(@RequestBody OrderItemDto dto) {
        dto.setId(0);
        dto = orderItemService.createOrderItem(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
