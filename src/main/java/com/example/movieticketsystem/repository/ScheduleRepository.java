package com.example.movieticketsystem.repository;

import com.example.movieticketsystem.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByMovie_MovieId(Integer movieId);
    List<Schedule> findByCinema_CinemaId(Integer cinemaId);
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Schedule> findByMovie_MovieIdAndCinema_CinemaId(Integer movieId, Integer cinemaId);
}    