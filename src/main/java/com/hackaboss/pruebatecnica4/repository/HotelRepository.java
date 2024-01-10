package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByHotelCode(String hotelCode);

    Optional<Hotel> findByHotelCodeAndHotelNameAndHotelPlace(String hotelCode,
                                                             String hotelName,
                                                             String hotelPlace);

    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.hotelRooms r WHERE h.hotelPlace = :place " +
            "AND r.isBooked = :isBooked " +
            "AND r.dateFrom <= :dateFrom AND r.dateTo >= :dateTo")

    Optional<List<Hotel>> getHotels(@Param("dateFrom") LocalDate dateFrom,
                                   @Param("dateTo") LocalDate dateTo,
                                   @Param("place") String place,
                                   @Param("isBooked") boolean isBooked);
}
