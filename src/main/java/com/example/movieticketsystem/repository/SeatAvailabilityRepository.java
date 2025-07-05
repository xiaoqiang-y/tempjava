package com.example.movieticketsystem.repository;

import com.example.movieticketsystem.model.entity.SeatAvailability;
import com.example.movieticketsystem.model.entity.Schedule;
import com.example.movieticketsystem.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SeatAvailabilityRepository extends JpaRepository<SeatAvailability, Integer> {
    List<SeatAvailability> findBySchedule(Schedule schedule);
    Optional<SeatAvailability> findByScheduleAndSeat(Schedule schedule, Seat seat);
    List<SeatAvailability> findByScheduleAndStatus(Schedule schedule, Integer status);
    List<SeatAvailability> findByUserUserIdAndLockedUntilAfter(Integer userId, java.sql.Timestamp timestamp);

    List<SeatAvailability> findByStatusAndLockedUntilBefore(Integer status, Timestamp lockedUntil);
}