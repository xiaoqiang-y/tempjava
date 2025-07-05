package com.example.movieticketsystem.service.impl;

import com.example.movieticketsystem.model.entity.Seat;
import com.example.movieticketsystem.model.entity.SeatAvailability;
import com.example.movieticketsystem.model.entity.Schedule;
import com.example.movieticketsystem.repository.SeatAvailabilityRepository;
import com.example.movieticketsystem.service.SeatAvailabilityService;
import com.example.movieticketsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatAvailabilityServiceImpl implements SeatAvailabilityService {

    private final SeatAvailabilityRepository seatAvailabilityRepository;
    private final ScheduleService scheduleService;

    @Override
    public List<SeatAvailability> getSeatAvailabilitiesBySchedule(Schedule schedule) {
        return seatAvailabilityRepository.findBySchedule(schedule);
    }

    @Override
    public Optional<SeatAvailability> getSeatAvailabilityByScheduleAndSeat(Schedule schedule, Integer seatId) {
        Seat seat = schedule.getHall().getSeats().stream()
                .filter(s -> s.getSeatId().equals(seatId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        return seatAvailabilityRepository.findByScheduleAndSeat(schedule, seat);
    }

    @Override
    @Transactional
    public SeatAvailability lockSeat(Integer scheduleId, Integer seatId, Integer userId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Seat seat = schedule.getHall().getSeats().stream()
                .filter(s -> s.getSeatId().equals(seatId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        SeatAvailability seatAvailability = seatAvailabilityRepository.findByScheduleAndSeat(schedule, seat)
                .orElseThrow(() -> new RuntimeException("Seat availability not found"));

        if (seatAvailability.getStatus() != 0) {
            throw new RuntimeException("Seat is not available");
        }

        seatAvailability.setStatus(2); // 锁定
        seatAvailability.setUser(null); // 实际应设置为当前用户
        seatAvailability.setLockedUntil(LocalDateTime.now().plusMinutes(15));

        return seatAvailabilityRepository.save(seatAvailability);
    }

    @Override
    @Transactional
    public boolean releaseExpiredLocks() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<SeatAvailability> expiredLocks = seatAvailabilityRepository.findByStatusAndLockedUntilBefore(2, now);

        for (SeatAvailability availability : expiredLocks) {
            availability.setStatus(0); // 恢复为可用
            availability.setUser(null);
            availability.setLockedUntil(null);
        }

        seatAvailabilityRepository.saveAll(expiredLocks);
        return true;
    }

    @Override
    @Transactional
    public boolean releaseUserLocks(Integer userId) {
        List<SeatAvailability> userLocks = seatAvailabilityRepository.findByUserUserIdAndLockedUntilAfter(userId, new Timestamp(System.currentTimeMillis()));

        for (SeatAvailability availability : userLocks) {
            availability.setStatus(0); // 恢复为可用
            availability.setUser(null);
            availability.setLockedUntil(null);
        }

        seatAvailabilityRepository.saveAll(userLocks);
        return true;
    }
}    