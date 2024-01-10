package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

    Optional<HotelReservation> findByDateFromAndDateToAndNightsAndPlaceAndHotelCodeAndPeopleQAndRoomType(LocalDate dateFrom,
                                                                                                         LocalDate dateTo,
                                                                                                         int nights,
                                                                                                         String place,
                                                                                                         String hotelCode,
                                                                                                         int peopleQ,
                                                                                                         String roomType);
}
