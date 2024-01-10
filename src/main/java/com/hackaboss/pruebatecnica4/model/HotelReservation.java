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
public class HotelReservation {

    @Id
    @GeneratedValue(generator = "hr-generator")
    @GenericGenerator(
            name = "hr-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "hr_name", value = "hr_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;
    private String place;
    private String hotelCode;
    private int peopleQ;
    private String roomType;
    private String hotelBooker;
    private double totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room")
    private Room roomData;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "ReservationRoomPerson",
            joinColumns = @JoinColumn(name = "ReservationRoomID"),
            inverseJoinColumns = @JoinColumn(name = "PersonID")
    )
    private List<Person> hosts;
}
