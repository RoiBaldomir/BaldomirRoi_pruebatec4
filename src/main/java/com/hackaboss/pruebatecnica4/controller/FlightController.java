package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;
import com.hackaboss.pruebatecnica4.service.IFlightService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency/flights")
public class FlightController {

    @Autowired
    private IFlightService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se han encontrado vuelos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<?> getFlights() {

        return service.getFlights();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado un vuelo con la id recibida"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getFlight(@PathVariable long id) {

       return service.getFlight(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se han encontrado vuelos según los parámetros"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/find")
    public ResponseEntity<?> getFlight(@RequestParam String dateFrom,
                                       @RequestParam String dateTo,
                                       @RequestParam String origin,
                                       @RequestParam String destination){

        return service.getFlights(dateFrom, dateTo, origin, destination);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vuelo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Ya existe un vuelo idéntico en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/new")
    public ResponseEntity<String> addFlight(@RequestBody FlightDTO flightDTO) {

        return service.addFlight(flightDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado el vuelo que intenta editar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editFlight(@PathVariable long id,
                                        @RequestParam String flightNumber,
                                        @RequestParam String origin,
                                        @RequestParam String destination,
                                        @RequestParam String seatType,
                                        @RequestParam double flightPrice,
                                        @RequestParam String outwardDate) {

        return service.editFlight(id, flightNumber, origin, destination, seatType, flightPrice, outwardDate);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar un vuelo con reservas activas"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado el vuelo que intenta eliminar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable long id) {

       return service.deleteFlight(id);
    }
}

