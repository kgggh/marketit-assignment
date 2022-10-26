package com.marketit.model.dto;

import com.marketit.model.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long orderItemId;
    private String itemName;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getName();
    }
}
