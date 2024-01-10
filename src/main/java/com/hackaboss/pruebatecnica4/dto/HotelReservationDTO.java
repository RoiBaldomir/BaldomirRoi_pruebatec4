package com.hackaboss.pruebatecnica4.dto;

import com.hackaboss.pruebatecnica4.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelReservationDTO {

    private String dateFrom;
    private String dateTo;
    private int nights;
    private String place;
    private String hotelCode;
    private int peopleQ;
    private String roomType;
    private List<Person> hosts;
}
