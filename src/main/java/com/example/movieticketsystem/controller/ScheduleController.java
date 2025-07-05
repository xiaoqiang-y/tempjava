package com.example.movieticketsystem.controller;

import com.example.movieticketsystem.model.entity.Schedule;
import com.example.movieticketsystem.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody @Valid Schedule schedule) {
        return new ResponseEntity<>(scheduleService.saveSchedule(schedule), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Integer id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        return schedule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Schedule>> getSchedulesByMovie(@PathVariable Integer movieId) {
        return new ResponseEntity<>(scheduleService.getSchedulesByMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<Schedule>> getSchedulesByCinema(@PathVariable Integer cinemaId) {
        return new ResponseEntity<>(scheduleService.getSchedulesByCinema(cinemaId), HttpStatus.OK);
    }

    @GetMapping("/timerange")
    public ResponseEntity<List<Schedule>> getSchedulesByTimeRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return new ResponseEntity<>(scheduleService.getSchedulesByTimeRange(start, end), HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}/cinema/{cinemaId}")
    public ResponseEntity<List<Schedule>> getSchedulesByMovieAndCinema(
            @PathVariable Integer movieId,
            @PathVariable Integer cinemaId
    ) {
        return new ResponseEntity<>(scheduleService.getSchedulesByMovieAndCinema(movieId, cinemaId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}    