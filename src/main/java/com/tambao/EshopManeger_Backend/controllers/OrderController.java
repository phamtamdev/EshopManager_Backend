package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.OrderConfirmationDto;
import com.tambao.EshopManeger_Backend.dto.OrderDto;
import com.tambao.EshopManeger_Backend.entity.OrderStatus;
import com.tambao.EshopManeger_Backend.service.Impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrder(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "sort-order", required = false) String sortOrder,
            @RequestParam(value = "status", required = false) OrderStatus status
    ) {
        if (page == null || size == null) {
            List<OrderDto> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else if (keyword != null && field != null) {
            Page<OrderDto> orders = orderService.getOrdersWithPageAndSortingAndSearch(page, size, keyword, field, sortOrder);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else if (keyword != null) {
            Page<OrderDto> orders = orderService.getOrdersWithPageAndSearch(page, size, keyword);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else if (field != null) {
            Page<OrderDto> orders = orderService.getOrdersWithPageAndSorting(page, size, field, sortOrder);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else if (status != null) {
            Page<OrderDto> orders = orderService.getOrdersWithPageAndOrderStatus(page, size, status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            Page<OrderDto> orders = orderService.getOrdersWithPage(page, size);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(orderService.getByUserId(userId));
    }

    @GetMapping("/{orderCode}/{phone}")
    public ResponseEntity<?> getOrdersByOrderCode(@PathVariable("phone") String phone, @PathVariable("orderCode") String orderCode) {
        return ResponseEntity.ok(orderService.getByOrderCodeAndPhone(orderCode, phone));
    }

    @GetMapping("/order-code")
    public ResponseEntity<?> getOrdersByOrderCode(@RequestParam("orderCode") String orderCode) {
        return ResponseEntity.ok(orderService.getOrderByOrderCode(orderCode));
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderDto order) {
        order.setId(0);
        order = orderService.createOrder(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/send-confirmation")
    public ResponseEntity<?> sendConfirmation(@RequestBody OrderConfirmationDto orderConfirmationDto) {
        orderService.sendEmail(orderConfirmationDto);
        return ResponseEntity.ok("Successfully sent confirmation email");
    }
}
