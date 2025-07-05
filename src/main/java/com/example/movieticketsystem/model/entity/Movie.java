package com.example.movieticketsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String genre;

    private String director;

    private String cast;

    private String coverImageUrl;

    private Double rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}    