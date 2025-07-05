package com.example.movieticketsystem.repository;

import com.example.movieticketsystem.model.entity.TicketOrder;
import com.example.movieticketsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {
    List<TicketOrder> findByUser(User user);
    Optional<TicketOrder> findByOrderNumber(String orderNumber);
}    