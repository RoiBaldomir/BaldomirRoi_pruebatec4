package com.hackaboss.pruebatecnica4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "person-generator")
    @GenericGenerator(
            name = "person-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "person_name", value = "person_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;

    @ManyToMany(mappedBy = "hosts")
    private List<HotelReservation> hotelReservations;

    @ManyToMany(mappedBy = "passengers")
    private List<FlightReservation> flightReservations;
}
