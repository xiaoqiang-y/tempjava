package com.example.movieticketsystem.service.impl;

import com.example.movieticketsystem.model.entity.Cinema;
import com.example.movieticketsystem.repository.CinemaRepository;
import com.example.movieticketsystem.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    @Override
    public Cinema saveCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    @Override
    public Optional<Cinema> getCinemaById(Integer id) {
        return cinemaRepository.findById(id);
    }

    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public List<Cinema> searchCinemas(String keyword) {
        List<Cinema> byName = cinemaRepository.findByNameContaining(keyword);
        List<Cinema> byAddress = cinemaRepository.findByAddressContaining(keyword);
        byName.addAll(byAddress);
        return byName;
    }

    @Override
    public void deleteCinema(Integer id) {
        cinemaRepository.deleteById(id);
    }
}    