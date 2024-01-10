package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.FlightReservationDTO;
import com.hackaboss.pruebatecnica4.model.FlightReservation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFlightReservationService {

    ResponseEntity<?> addFlightReservation(FlightReservationDTO flightReservationDTO);
    ResponseEntity<?> getFlightReservations();
    ResponseEntity<?> editFlightReservation(long id,
                                            String outwardDate,
                                            String origin,
                                            String destination,
                                            String flightCode,
                                            int peopleQ,
                                            String seatType,
                                            String flightBooker,
                                            double totalPrice);
    ResponseEntity<?> deleteFlightReservation(long id);
}
