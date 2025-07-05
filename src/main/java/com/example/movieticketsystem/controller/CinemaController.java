package com.example.movieticketsystem.controller;

import com.example.movieticketsystem.model.entity.Cinema;
import com.example.movieticketsystem.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> createCinema(@RequestBody @Valid Cinema cinema) {
        return new ResponseEntity<>(cinemaService.saveCinema(cinema), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinema(@PathVariable Integer id) {
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        return cinema.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        return new ResponseEntity<>(cinemaService.getAllCinemas(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cinema>> searchCinemas(@RequestParam String keyword) {
        return new ResponseEntity<>(cinemaService.searchCinemas(keyword), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Integer id) {
        cinemaService.deleteCinema(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}    