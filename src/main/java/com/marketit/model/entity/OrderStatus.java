package com.marketit.model.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    REQUEST("요청"),
    ACCEPT("접수"),
    COMPLETE("완료");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
