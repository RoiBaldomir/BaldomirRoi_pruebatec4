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
public class FlightReservation {

    @Id
    @GeneratedValue(generator = "fr-generator")
    @GenericGenerator(
            name = "fr-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "fr_name", value = "fr_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private LocalDate date;
    private String origin;
    private String destination;
    private String flightCode;
    private String seatType;
    private String flightBooker;
    private int peopleQ;
    private double totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "flight")
    private Flight flightData;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "ReservationFlightPerson",
            joinColumns = @JoinColumn(name = "ReservationFlightID"),
            inverseJoinColumns = @JoinColumn(name = "PersonID")
    )
    private List<Person> passengers;
}
