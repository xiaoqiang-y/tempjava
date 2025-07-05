package com.example.movieticketsystem.service.impl;

import com.example.movieticketsystem.model.entity.Schedule;
import com.example.movieticketsystem.repository.ScheduleRepository;
import com.example.movieticketsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Optional<Schedule> getScheduleById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<Schedule> getSchedulesByMovie(Integer movieId) {
        return scheduleRepository.findByMovie_MovieId(movieId);
    }

    @Override
    public List<Schedule> getSchedulesByCinema(Integer cinemaId) {
        return scheduleRepository.findByCinema_CinemaId(cinemaId);
    }

    @Override
    public List<Schedule> getSchedulesByTimeRange(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    @Override
    public List<Schedule> getSchedulesByMovieAndCinema(Integer movieId, Integer cinemaId) {
        return scheduleRepository.findByMovie_MovieIdAndCinema_CinemaId(movieId, cinemaId);
    }

    @Override
    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);
    }
}    