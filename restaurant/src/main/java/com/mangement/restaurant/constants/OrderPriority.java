package com.mangement.restaurant.constants;

public enum OrderPriority {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int value;

    OrderPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
