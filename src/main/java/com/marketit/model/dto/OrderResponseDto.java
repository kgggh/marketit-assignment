package com.marketit.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marketit.model.entity.Order;
import com.marketit.model.entity.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponseDto {
    private Long orderId;
    private String email;
    private OrderStatus status;

    private List<OrderItemResponseDto> orderItems;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/seoul")
    private LocalDateTime orderDateTime;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.email = order.getEmail();
        this.status = order.getStatus();
        this.orderDateTime = order.getOrderDateTime();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }
}
