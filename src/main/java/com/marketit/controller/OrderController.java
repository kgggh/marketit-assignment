package com.marketit.controller;

import com.marketit.global.ResponseVO;
import com.marketit.model.dto.OrderResponseDto;
import com.marketit.model.entity.Order;
import com.marketit.repository.ItemRepository;
import com.marketit.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemRepository itemRepository;

    @GetMapping(path = "/api/orders/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable("id") Long orderId) {
        Order order = orderService.getOrder(orderId);
        OrderResponseDto result = new OrderResponseDto(order);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/api/orders")
    public ResponseEntity<Object> getOrders() {
        List<Order> orders = orderService.getOrders();
        List<OrderResponseDto> result = orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PatchMapping(path = "/api/orders/{id}/complete")
    public ResponseEntity<Object> changeOrderStatusToComplete(@PathVariable("id") Long orderId) {
        log.info(String.format("order status change to complete, request order-id=%d", orderId));
        orderService.complete(orderId);
        return ResponseEntity.ok(ResponseVO.success());
    }

    @PatchMapping(path = "/api/orders/{id}/accept")
    public ResponseEntity<Object> changeOrderStatusToAccept(@PathVariable("id") Long orderId) {
        log.info(String.format("order status change to accept, request order-id=%d", orderId));
        orderService.accept(orderId);
        return ResponseEntity.ok(ResponseVO.success());
    }
}
