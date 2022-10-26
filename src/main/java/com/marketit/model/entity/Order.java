package com.marketit.model.entity;

import com.marketit.service.OrderStatusChangeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDateTime;

    private Order(String email, OrderItem... orderItems) {
        this.email = email;
        this.status = OrderStatus.REQUEST;
        this.orderDateTime = LocalDateTime.now();
        for (OrderItem item : orderItems) {
            item.setOrder(this);
            this.orderItems.add(item);
        }
    }

    public static Order createOrder(String email, OrderItem... orderItems) {
        return new Order(email, orderItems);
    }

    public void accept() {
        if(this.status == OrderStatus.ACCEPT) {
            throw new OrderStatusChangeException("This is an order that has already been placed");
        }
        if(this.status == OrderStatus.COMPLETE) {
            throw new OrderStatusChangeException("This is an order that has already been fulfilled");
        }
        this.status = OrderStatus.ACCEPT;
    }

    public void complete() {
        if(this.status == OrderStatus.REQUEST) {
            throw new OrderStatusChangeException("This is a pending order");
        }

        if(this.status == OrderStatus.COMPLETE) {
            throw new OrderStatusChangeException("This is a completed order");
        }
        this.status = OrderStatus.COMPLETE;
    }
}
