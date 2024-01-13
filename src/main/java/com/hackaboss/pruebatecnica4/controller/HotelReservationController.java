package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.HotelReservationDTO;
import com.hackaboss.pruebatecnica4.service.IHotelReservationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency/hotel-booking")
public class HotelReservationController {

    @Autowired
    private IHotelReservationService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400",
                    description = "Ya existe una reserva idéntica en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/new")
    public ResponseEntity<?> addHotelReservation(@RequestBody HotelReservationDTO hotelReservationDTO) {

        return service.addHotelReservation(hotelReservationDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<?> getHotelReservations() {

       return service.getHotelReservations();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reserva"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> getHotelReservation(@PathVariable long id) {

        return service.getHotelReservation(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reserva que intenta editar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editHotelReservations(@PathVariable long id,
                                                   @RequestParam String dateFrom,
                                                   @RequestParam String dateTo,
                                                   @RequestParam int nights,
                                                   @RequestParam String place,
                                                   @RequestParam String hotelCode,
                                                   @RequestParam int peopleQ,
                                                   @RequestParam String roomType,
                                                   @RequestParam double totalPrice) {

        return service.editHotelReservation(id, dateFrom, dateTo, nights, place, hotelCode, peopleQ, roomType, totalPrice);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado una reserva con la id recibida"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotelReservations(@PathVariable long id) {

        return service.deleteHotelReservation(id);
    }
}
