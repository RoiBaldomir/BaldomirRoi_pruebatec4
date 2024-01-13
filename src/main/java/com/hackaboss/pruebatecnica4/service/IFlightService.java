package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;
import org.springframework.http.ResponseEntity;

public interface IFlightService {

   ResponseEntity<?> getFlights();
   ResponseEntity<?> getFlight(long id);
   ResponseEntity<?> getFlights(String dateFrom, String dateTo, String origin, String destination);
   ResponseEntity<String> addFlight(FlightDTO flightDTO);
   ResponseEntity<?> editFlight(long id,
                     String flightNumber,
                     String origin,
                     String destination,
                     String seatType,
                     double flightPrice,
                     String outwardDate);
   ResponseEntity<String> deleteFlight(long id);
}
