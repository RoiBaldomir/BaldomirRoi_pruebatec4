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
public class Flight {

    @Id
    @GeneratedValue(generator = "flight-generator")
    @GenericGenerator(
            name = "flight-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "flight_name", value = "flight_sequence"),
                    @Parameter(name = "initial_value", value = "13"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private double flightPrice;
    private LocalDate outwardDate;

    @JsonIgnore
    @OneToMany(mappedBy = "flightData")
    private List<FlightReservation> reservations;
}
