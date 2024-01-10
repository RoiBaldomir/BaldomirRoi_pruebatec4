package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.HotelReservationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IHotelReservationService {

    ResponseEntity<?> addHotelReservation(HotelReservationDTO hotelReservationDTO);
    ResponseEntity<?> getHotelReservations();
    ResponseEntity<?> editHotelReservation(long id,
                                          String dateFrom,
                                          String dateTo,
                                          int nights,
                                          String place,
                                          String hotelCode,
                                          int peopleQ,
                                          String roomType,
                                          double totalPrice);
    ResponseEntity<?> deleteHotelReservation(long id);
}
