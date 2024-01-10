package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByDestinationAndFlightNumberAndFlightPriceAndOriginAndOutwardDateAndSeatType(String destination,
                                                                                            String flightNumber,
                                                                                            double flightPrice,
                                                                                            String origin,
                                                                                            LocalDate outwardDate,
                                                                                            String seatType);

    @Query("SELECT f FROM Flight f " +
            "WHERE f.origin = :origin " +
            "AND f.destination = :destination " +
            "AND (f.outwardDate BETWEEN :dateTo AND :dateFrom)")
    Optional<List<Flight>> findFlights(@Param("dateTo") LocalDate dateTo,
                                      @Param("dateFrom") LocalDate dateFrom,
                                      @Param("origin") String origin,
                                      @Param("destination") String destination);
}
