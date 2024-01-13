package com.hackaboss.pruebatecnica4.controller;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import com.hackaboss.pruebatecnica4.service.IHotelService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency/hotels")
public class HotelController {

    @Autowired
    private IHotelService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se han encontrado hoteles"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public ResponseEntity<?> getHotels() {

        return service.getHotels();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado un hotel con la id recibida"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotel(@PathVariable long id) {

      return service.getHotel(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se han encontrado hoteles según los parámetros"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/find")
    public ResponseEntity<?> getHotels(@RequestParam String dateFrom,
                                            @RequestParam String dateTo,
                                            @RequestParam String place) {

       return service.getHotels(dateFrom, dateTo, place);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Ya existe un hotel idéntico en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/new")
    public ResponseEntity<String> addHotel(@RequestBody HotelDTO hotelDTO) {

        return service.addHotel(hotelDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel que intenta editar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editHotel(@PathVariable long id,
                                            @RequestParam String hotelCode,
                                            @RequestParam String name,
                                            @RequestParam String place,
                                            @RequestParam String roomType,
                                            @RequestParam double roomPrice,
                                            @RequestParam String dateTo,
                                            @RequestParam String dateFrom,
                                            @RequestParam boolean isBooked) {

        return service.editHotel(id, hotelCode, name, place, roomType, roomPrice, dateTo, dateFrom, isBooked);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La operación se ejecutó correctamente"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar un hotel con reservas activas"),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel que intenta eliminar"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable long id) {

        return service.deleteHotel(id);
    }
}
