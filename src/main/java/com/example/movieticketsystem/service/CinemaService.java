package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    Cinema saveCinema(Cinema cinema);
    Optional<Cinema> getCinemaById(Integer id);
    List<Cinema> getAllCinemas();
    List<Cinema> searchCinemas(String keyword);
    void deleteCinema(Integer id);
}    