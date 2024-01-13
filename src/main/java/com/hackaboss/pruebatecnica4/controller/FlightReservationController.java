package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.FlightReservationDTO;
import com.hackaboss.pruebatecnica4.service.IFlightReservationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency/flight-booking")
public class FlightReservationController {

    @Autowired
    private IFlightReservationService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400",
                    description = "Ya existe una reserva idéntica en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/new")
    public ResponseEntity<?> addFlightReservation(@RequestBody FlightReservationDTO flightReservationDTO) {

        return service.addFlightReservation(flightReservationDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<?> getFlightReservations() {

        return service.getFlightReservations();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reserva"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> getFlightReservation(@PathVariable long id) {

        return service.getFlightReservation(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reserva que intenta editar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editFlightReservation(@PathVariable long id,
                                                   @RequestParam String outwardDate,
                                                   @RequestParam String origin,
                                                   @RequestParam String destination,
                                                   @RequestParam String flightCode,
                                                   @RequestParam int peopleQ,
                                                   @RequestParam String seatType,
                                                   @RequestParam String flightBooker,
                                                   @RequestParam double totalPrice) {

        return service.editFlightReservation(id, outwardDate, origin, destination, flightCode, peopleQ, seatType, flightBooker, totalPrice);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado una reserva con la id recibida"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlightReservation(@PathVariable long id) {

        return service.deleteFlightReservation(id);
    }
}
