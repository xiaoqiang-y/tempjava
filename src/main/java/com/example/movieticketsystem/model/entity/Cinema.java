package com.example.movieticketsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cinema")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cinemaId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phone;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hall> halls;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}    