package com.example.movieticketsystem.controller;

import com.example.movieticketsystem.model.entity.TicketOrder;
import com.example.movieticketsystem.model.request.CreateOrderRequest;
import com.example.movieticketsystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<TicketOrder> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketOrder> getOrder(@PathVariable Integer id) {
        Optional<TicketOrder> order = orderService.getOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketOrder>> getOrdersByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(orderService.getOrdersByUser(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Void> payOrder(@PathVariable Integer id) {
        orderService.payOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}    