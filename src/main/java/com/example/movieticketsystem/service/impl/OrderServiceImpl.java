package com.example.movieticketsystem.service.impl;

import com.example.movieticketsystem.model.entity.*;
import com.example.movieticketsystem.model.request.CreateOrderRequest;
import com.example.movieticketsystem.repository.TicketOrderRepository;
import com.example.movieticketsystem.service.AuthService;
import com.example.movieticketsystem.service.OrderService;
import com.example.movieticketsystem.service.SeatAvailabilityService;
import com.example.movieticketsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final TicketOrderRepository ticketOrderRepository;
    private final ScheduleService scheduleService;
    private final AuthService authService;
    private final SeatAvailabilityService seatAvailabilityService;

    @Override
    @Transactional
    public TicketOrder createOrder(CreateOrderRequest request) {
        Schedule schedule = scheduleService.getScheduleById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        User user = authService.getCurrentUser();

        TicketOrder order = TicketOrder.builder()
                .user(user)
                .orderNumber(generateOrderNumber())
                .totalAmount(calculateTotalAmount(schedule, request.getSeatIds()))
                .status(0) // 待支付
                .createdAt(LocalDateTime.now())
                .build();

        TicketOrder savedOrder = ticketOrderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Integer seatId : request.getSeatIds()) {
            // 锁定座位
            seatAvailabilityService.lockSeat(request.getScheduleId(), seatId, user.getUserId());

            Seat seat = schedule.getHall().getSeats().stream()
                    .filter(s -> s.getSeatId().equals(seatId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .schedule(schedule)
                    .seat(seat)
                    .quantity(1)
                    .price(schedule.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        savedOrder.setOrderItems(orderItems);
        return ticketOrderRepository.save(savedOrder);
    }

    @Override
    public Optional<TicketOrder> getOrderById(Integer id) {
        return ticketOrderRepository.findById(id);
    }

    @Override
    public List<TicketOrder> getOrdersByUser(Integer userId) {
        User user = new User();
        user.setUserId(userId);
        return ticketOrderRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer orderId) {
        TicketOrder order = ticketOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != 0) {
            throw new RuntimeException("Cannot cancel order with current status");
        }

        // 释放座位
        for (OrderItem item : order.getOrderItems()) {
            seatAvailabilityService.releaseUserLocks(order.getUser().getUserId());
        }

        order.setStatus(2); // 已取消
        ticketOrderRepository.save(order);
    }

    @Override
    @Transactional
    public void payOrder(Integer orderId) {
        TicketOrder order = ticketOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != 0) {
            throw new RuntimeException("Order is not in pending payment status");
        }

        // 检查座位是否仍然可用
        for (OrderItem item : order.getOrderItems()) {
            SeatAvailability availability = seatAvailabilityService.getSeatAvailabilityByScheduleAndSeat(
                    item.getSchedule(), item.getSeat().getSeatId()
            ).orElseThrow(() -> new RuntimeException("Seat availability not found"));

            if (availability.getStatus() != 2) { // 不是锁定状态
                throw new RuntimeException("Seat is no longer available");
            }
        }

        // 更新座位状态为已售
        for (OrderItem item : order.getOrderItems()) {
            SeatAvailability availability = seatAvailabilityService.getSeatAvailabilityByScheduleAndSeat(
                    item.getSchedule(), item.getSeat().getSeatId()
            ).get();
            availability.setStatus(1); // 已售
            availability.setUser(order.getUser());
            availability.setLockedUntil(null);
        }

        order.setStatus(1); // 已支付
        order.setPaidAt(LocalDateTime.now());
        ticketOrderRepository.save(order);
    }

    private String generateOrderNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    private Double calculateTotalAmount(Schedule schedule, List<Integer> seatIds) {
        return schedule.getPrice() * seatIds.size();
    }
}    