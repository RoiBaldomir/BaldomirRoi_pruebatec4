package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IHotelService {

    ResponseEntity<?> getHotels();
    ResponseEntity<?> getHotels(String dateFrom, String dateTo, String place);
    ResponseEntity<?> getHotel(long id);
    ResponseEntity<?> addHotel(HotelDTO hotelDTO);
    ResponseEntity<?> editHotel(long id,
                    String hotelCode,
                    String hotelName,
                    String hotelPlace,
                    String roomType,
                    double roomPrice,
                    String dateTo,
                    String dateFrom,
                    boolean isBooked);
    ResponseEntity<?> deleteHotel(long id);
}
