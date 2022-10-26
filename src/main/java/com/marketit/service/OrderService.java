package com.marketit.service;

import com.marketit.model.dto.OrderSearchRequestDto;
import com.marketit.model.entity.Order;

import java.util.List;

public interface OrderService {
    Long order(String email, Long itemId);
    void accept(Long orderId);
    void complete(Long orderId);

    Order getOrder(Long orderId);
    List<Order> getOrders();
    List<Order> getOrders(OrderSearchRequestDto orderSearchRequestDto);
}
