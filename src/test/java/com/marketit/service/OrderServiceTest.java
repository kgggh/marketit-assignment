package com.marketit.service;

import com.marketit.model.entity.Item;
import com.marketit.model.entity.Order;
import com.marketit.model.entity.OrderStatus;
import com.marketit.repository.ItemRepository;
import com.marketit.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;

    @BeforeAll
    void init() {
        for (int i = 0; i < 5; i++) {
            Item item = Item.createItem("잡지");
            itemRepository.save(item);
        }
    }

    @Test
    void 신규_주문요청 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;

        /* when */
        Long orderId = orderService.order(userEmail, itemId);
        Order newestOrder = orderRepository.findById(orderId).get();

        /* then */
        assertEquals(orderId, newestOrder.getId());
        assertEquals(newestOrder.getStatus(), OrderStatus.REQUEST);
    }

    @Test
    void 요청주문건_상태를_접수상태로_변경 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);

        /* when */
        orderService.accept(orderId);
        Order newestOrder = orderRepository.findById(orderId).get();

        /* then */
        assertEquals(newestOrder.getStatus(), OrderStatus.ACCEPT);
    }

    @Test
    void 접수주문건_상태를_접수상태로_변경할수_없다 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);
        String receiveMsg = "This is an order that has already been placed";
        orderService.accept(orderId);

        /* when */
        OrderStatusChangeException exception =
                assertThrows(OrderStatusChangeException.class, () -> orderService.accept(orderId));

        /* then */
        assertEquals(exception.getMessage(), receiveMsg);
    }

    @Test
    void 요청주문건_상태를_완료상태로_변경할수_없다 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);
        String receiveMsg = "This is a pending order";

        /* when */
        OrderStatusChangeException exception =
                assertThrows(OrderStatusChangeException.class, () -> orderService.complete(orderId));

        /* then */
        assertEquals(exception.getMessage(), receiveMsg);
    }

    @Test
    void 주문접수건_상태를_완료상태로_변경 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);

        /* when */
        orderService.accept(orderId);
        orderService.complete(orderId);
        Order newestOrder = orderRepository.findById(orderId).get();

        /* then */
        assertEquals(newestOrder.getStatus(), OrderStatus.COMPLETE);
    }

    @Test
    void 완료주문건_상태를_접수상태로_변경할수_없다 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);
        String receiveMsg = "This is an order that has already been fulfilled";
        orderService.accept(orderId);
        orderService.complete(orderId);

        /* when */
        OrderStatusChangeException exception =
                assertThrows(OrderStatusChangeException.class, () -> orderService.accept(orderId));

        /* then */
        assertEquals(exception.getMessage(), receiveMsg);
    }


    @Test
    void 완료주문건건_상태를_완료상태로_변경할수_없다 () {
        /* given */
        String userEmail = "tester@gmail.com";
        Long itemId = 1L;
        Long orderId = orderService.order(userEmail, itemId);
        String receiveMsg = "This is a completed order";
        orderService.accept(orderId);
        orderService.complete(orderId);

        /* when */
        OrderStatusChangeException exception =
                assertThrows(OrderStatusChangeException.class, () -> orderService.complete(orderId));

        /* then */
        assertEquals(exception.getMessage(), receiveMsg);
    }
}