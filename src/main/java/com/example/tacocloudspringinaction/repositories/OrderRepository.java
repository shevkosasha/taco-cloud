package com.example.tacocloudspringinaction.repositories;

import com.example.tacocloudspringinaction.models.Order;

public interface OrderRepository {
    Order save(Order order);
}
