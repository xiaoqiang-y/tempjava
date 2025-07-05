package com.example.movieticketsystem.service;

import com.example.movieticketsystem.model.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie saveMovie(Movie movie);
    Optional<Movie> getMovieById(Integer id);
    List<Movie> getAllMovies();
    List<Movie> searchMovies(String title);
    void deleteMovie(Integer id);
}    