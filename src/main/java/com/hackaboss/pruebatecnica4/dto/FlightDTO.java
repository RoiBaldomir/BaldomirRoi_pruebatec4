package com.hackaboss.pruebatecnica4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FlightDTO {

    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private double flightPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate outwardDate;
}
