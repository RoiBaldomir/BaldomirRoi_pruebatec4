package com.hackaboss.pruebatecnica4.dto;

import com.hackaboss.pruebatecnica4.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightReservationDTO {

    private String date;
    private String origin;
    private String destination;
    private String flightCode;
    private int peopleQ;
    private String seatType;
    private List<Person> passengers;
}
