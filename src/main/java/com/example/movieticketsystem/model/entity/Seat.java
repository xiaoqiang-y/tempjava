package com.example.movieticketsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(nullable = false)
    private Integer rowNum;

    @Column(nullable = false)
    private Integer columnNum;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'normal'")
    private String seatType;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatAvailability> seatAvailabilities;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}    