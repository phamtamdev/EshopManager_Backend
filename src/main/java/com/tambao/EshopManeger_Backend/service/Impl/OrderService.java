package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.Utils.OrderCodeGenerator;
import com.tambao.EshopManeger_Backend.dto.OrderDto;
import com.tambao.EshopManeger_Backend.entity.OrderStatus;
import com.tambao.EshopManeger_Backend.entity.Orders;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.OrderMapper;
import com.tambao.EshopManeger_Backend.repository.OrderRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orderDto.setOrderCode(OrderCodeGenerator.generateOrderCode());
        Orders order = orderRepository.save(OrderMapper.mapToOrders(orderDto));
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public OrderDto getOrderByOrderCode(String orderCode) {
        Orders order = orderRepository.findByOrderCode(orderCode);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::mapToOrderDto).collect(Collectors.toList());
    }

    @Override
    public Page<OrderDto> getOrdersWithPage(int page, int size) {
        Page<Orders> orders = orderRepository.findAll(PageRequest.of(page, size));
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public Page<OrderDto> getOrdersWithPageAndSearch(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orders = orderRepository.findByOrderCode(keyword, pageable);
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public Page<OrderDto> getOrdersWithPageAndOrderStatus(int page, int size, OrderStatus orderStatus) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orders = orderRepository.findByOrderStatus(orderStatus, pageable);
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public Page<OrderDto> getOrdersWithPageAndSorting(int page, int size, String field, String sortOrder) {
        Pageable pageable = createPageable(page, size, field, sortOrder);
        Page<Orders> orders = orderRepository.findAll(pageable);
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public Page<OrderDto> getOrdersWithPageAndSortingAndSearch(int page, int size, String field, String sortOrder, String keyword) {
        Pageable pageable = createPageable(page, size, field, sortOrder);
        Page<Orders> orders = orderRepository.findByOrderCode(keyword, pageable);
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public OrderDto getByOrderCodeAndPhone(String orderCode, String phone) {
        Users users = userRepository.findByPhone(phone);
        if (users == null) {
            throw new ResourceNotFoundException("User not found");
        }
        Orders orders = orderRepository.findByOrderCodeAndUserId(orderCode, users.getId());
        return OrderMapper.mapToOrderDto(orders);
    }

    @Override
    public List<OrderDto> getByUserId(Integer userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<Orders> orders = orderRepository.findByUserId(users.getId());
        if (orders.isEmpty()) {
            return null;
        }
        return orders.stream().map(OrderMapper::mapToOrderDto).collect(Collectors.toList());
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }
}
