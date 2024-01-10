package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.FlightReservation;
import com.hackaboss.pruebatecnica4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {

    Optional<FlightReservation> findByDateAndOriginAndDestinationAndFlightCodeAndPeopleQAndSeatType(LocalDate date,
                                                                                                     String origin,
                                                                                                     String destination,
                                                                                                     String flightCode,
                                                                                                     int peopleQ,
                                                                                                     String seatType);
}
