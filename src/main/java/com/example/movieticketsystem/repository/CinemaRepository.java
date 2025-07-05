package com.example.movieticketsystem.repository;

import com.example.movieticketsystem.model.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    List<Cinema> findByNameContaining(String name);
    List<Cinema> findByAddressContaining(String address);
}    