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
public class Hotel {

    @Id
    @GeneratedValue(generator = "hotel-generator")
    @GenericGenerator(
            name = "hotel-generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "hotel_name", value = "hotel_sequence"),
                    @Parameter(name = "initial_value", value = "13"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private String hotelCode;
    private String hotelName;
    private String hotelPlace;

    @OneToMany(mappedBy = "hotel")
    private List<Room> hotelRooms;
}
