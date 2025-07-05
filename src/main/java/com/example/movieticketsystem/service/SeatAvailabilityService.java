package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.SeatAvailability;
import com.example.movieticketsystem.model.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface SeatAvailabilityService {
    List<SeatAvailability> getSeatAvailabilitiesBySchedule(Schedule schedule);
    Optional<SeatAvailability> getSeatAvailabilityByScheduleAndSeat(Schedule schedule, Integer seatId);
    SeatAvailability lockSeat(Integer scheduleId, Integer seatId, Integer userId);
    boolean releaseExpiredLocks();
    boolean releaseUserLocks(Integer userId);
}    