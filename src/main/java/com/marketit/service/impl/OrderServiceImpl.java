package com.marketit.service.impl;

import com.marketit.model.dto.OrderSearchRequestDto;
import com.marketit.model.entity.Item;
import com.marketit.model.entity.Order;
import com.marketit.model.entity.OrderItem;
import com.marketit.repository.ItemRepository;
import com.marketit.repository.OrderRepository;
import com.marketit.service.ItemException;
import com.marketit.service.OrderNotExistException;
import com.marketit.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Value("${order.enable-mock}")
    private boolean enableMock = true;

    @PostConstruct
    private void makeMock() {
        log.info("[MOCK-DATA] Make Order Data={}", enableMock);
        if(enableMock) {
            for (int i = 0; i < 10; i++) {
                Item item = Item.createItem("잡지");
                Long itemId = itemRepository.save(item).getId();
                this.order("tester@gmail.com", itemId);
            }
        }
    }

    @Transactional
    @Override
    public Long order(String email, Long itemId) {
        log.info(String.format( "orderer info email=%s, item-id=%s ",email, itemId));
        Item item = this.getItem(itemId);
        OrderItem newOrderItem = OrderItem.createOrderItem(item);
        Order newOrder = Order.createOrder(email, newOrderItem);
        return orderRepository.save(newOrder).getId();
    }

    @Transactional(readOnly = true)
    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException("Not Found This Item Id=" + itemId));
    }

    @Transactional
    @Override
    public void accept(Long orderId) {
        Order order = getOrder(orderId);
        order.accept();
    }

    @Transactional
    @Override
    public void complete(Long orderId) {
        Order order = getOrder(orderId);
        order.complete();
    }

    @Transactional(readOnly = true)
    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotExistException("Not Found This Order Id=" + orderId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrders(OrderSearchRequestDto orderSearchRequestDto) {
        /* Todo */
        return null;
    }
}
