package com.example.movieticketsystem.model.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "hall")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hallId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(columnDefinition = "JSON")
    private JSONObject seatLayout;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}    