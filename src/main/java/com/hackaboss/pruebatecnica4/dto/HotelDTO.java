package com.hackaboss.pruebatecnica4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HotelDTO {

    private String hotelCode;
    private String name;
    private String place;
    private String roomType;
    private double roomPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDate dateTo;
    private boolean isBooked;
}
