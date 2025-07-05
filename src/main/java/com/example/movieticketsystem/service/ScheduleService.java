package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule saveSchedule(Schedule schedule);
    Optional<Schedule> getScheduleById(Integer id);
    List<Schedule> getSchedulesByMovie(Integer movieId);
    List<Schedule> getSchedulesByCinema(Integer cinemaId);
    List<Schedule> getSchedulesByTimeRange(LocalDateTime start, LocalDateTime end);
    List<Schedule> getSchedulesByMovieAndCinema(Integer movieId, Integer cinemaId);
    void deleteSchedule(Integer id);
}    