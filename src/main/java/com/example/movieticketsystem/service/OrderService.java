package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.TicketOrder;
import com.example.movieticketsystem.model.request.CreateOrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    TicketOrder createOrder(CreateOrderRequest request);
    Optional<TicketOrder> getOrderById(Integer id);
    List<TicketOrder> getOrdersByUser(Integer userId);
    void cancelOrder(Integer orderId);
    void payOrder(Integer orderId);
}    