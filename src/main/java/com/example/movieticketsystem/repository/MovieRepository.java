package com.example.movieticketsystem.repository;

import com.example.movieticketsystem.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleContaining(String title);
}    