package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.Utils.OrderCodeGenerator;
import com.tambao.EshopManeger_Backend.dto.OrderConfirmationDto;
import com.tambao.EshopManeger_Backend.dto.OrderDto;
import com.tambao.EshopManeger_Backend.dto.OrderItemDto;
import com.tambao.EshopManeger_Backend.entity.*;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.OrderMapper;
import com.tambao.EshopManeger_Backend.repository.*;
import com.tambao.EshopManeger_Backend.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

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
    public OrderDto getByOrderId(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return OrderMapper.mapToOrderDto(orders);
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
        Page<Orders> orders = orderRepository.findByOrderCodeContainingIgnoreCase(keyword, pageable);
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
        Page<Orders> orders = orderRepository.findByOrderCodeContainingIgnoreCase(keyword, pageable);
        return orders.map(OrderMapper::mapToOrderDto);
    }

    @Override
    public OrderDto getByOrderCodeAndPhone(String orderCode, String phone) {
        Users users = userRepository.findByPhone(phone);
        if (users == null) {
            return null;
        }
        Orders orders = orderRepository.findByOrderCodeAndUserId(orderCode, users.getId());
        if (orders == null) {
            return null;
        }
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

    @Override
    public OrderDto updateStatusOrder(Integer orderId, OrderStatus orderStatus) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orders.setOrderStatus(orderStatus);
        orderRepository.save(orders);
        return OrderMapper.mapToOrderDto(orders);
    }

    public String getFormattedPrice(Double price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(price);
    }

    @Override
    public void sendEmail(OrderConfirmationDto orderConfirmationDto) {
        Orders order = orderRepository.findById(orderConfirmationDto.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
        Payment payment = paymentRepository.findById(order.getPayment().getId()).orElseThrow(() -> new ResourceNotFoundException("Payment Not Found"));
        List<OrderItem> orderItems = orderItemRepository.findByOrdersId(order.getId());
        if(orderItems.isEmpty()) {
            return;
        }
        String subject = "Xác nhận đơn hàng";
        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html><html lang=\"vi\"><head><meta charset=\"UTF-8\"><title>Xác nhận đơn hàng</title></head><body>");
        body.append("<h1>Chào ").append(orderConfirmationDto.getFullName()).append(",</h1>");
        body.append("<p>Chúc mừng! Đơn hàng của bạn trên Eshop đã được xử lý thành công.</p>");
        body.append("<h2>Thông tin xác nhận đơn hàng:</h2>");
        body.append("<p>Mã đơn hàng: <strong>").append(order.getOrderCode()).append("</strong></p>");
        body.append("<p>Phương thức thanh toán: <strong>").append(payment.getName()).append("</strong></p>");
        body.append("<p>Sản phẩm đã đặt: <strong>").append(orderItems.size()).append("</strong></p>");
        body.append("<p>Tổng tiền sản phẩm: <strong>").append(getFormattedPrice(order.getTotalPrice())).append("</strong></p>");
        body.append("<p>Tổng tiền thanh toán: <strong>").append(getFormattedPrice(order.getTotalAmount())).append("</strong></p>");
        body.append("<h3>Chi tiết đơn hàng của bạn:</h3>");
        body.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"10\"><thead><tr><th>Hình Ảnh</th><th>Sản Phẩm</th><th>Số Lượng</th><th>Giá</th><th>Tổng</th></tr></thead><tbody>");

        for (OrderItem item : orderItems) {
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
            ProductImage productImage = productImageRepository.findByProductIdAndIcon(product.getId(), true);
            body.append("<tr>");
            body.append("<td><img src=\"").append(productImage.getData()).append("\" alt=\"")
                    .append(productImage.getName()).append("\" width=\"100\"></td>");
            body.append("<td>").append(product.getName()).append("</td>");
            body.append("<td>").append(item.getQuantity()).append("</td>");
            body.append("<td>").append(getFormattedPrice(item.getPrice())).append("</td>");
            body.append("<td>").append(getFormattedPrice(item.getPrice() * item.getQuantity())).append("</td>");
            body.append("</tr>");
        }

        body.append("</tbody></table>");
        body.append("<p>Cảm ơn bạn đã mua sắm tại Eshop!</p>");
        body.append("</body></html>");

        emailService.sendEmail("tambao11223344@gmail.com", orderConfirmationDto.getEmail(), subject, body.toString());
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }
}
