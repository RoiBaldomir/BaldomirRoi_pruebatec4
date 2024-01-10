package com.hackaboss.pruebatecnica4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(generator = "room-generator")
    @GenericGenerator(
            name = "room-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "room_name", value = "room_sequence"),
                    @Parameter(name = "initial_value", value = "13"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private String roomType;
    private double roomPrice;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean isBooked;

    @JsonIgnore
    @OneToMany(mappedBy = "roomData")
    private List<HotelReservation> reservations;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hotelID")
    private Hotel hotel;
}
