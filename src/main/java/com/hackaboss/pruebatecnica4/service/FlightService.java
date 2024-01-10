package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.FlightDTO;
import com.hackaboss.pruebatecnica4.model.Flight;
import com.hackaboss.pruebatecnica4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository repository;

    @Override
    public ResponseEntity<?> getFlights() {

        // Si se recibe una lista vacía se devuelve el status code correspondiente
        if (repository.findAll().isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("(404) No se han encontrado vuelos.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getFlight(long id) {

        // Se busca el vuelo correspondiente a la id en la base de datos
        Flight flight = repository.findById(id).orElse(null);

        // Si el vuelo es nulo se devuelve el status code correspondiente
        if (flight == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado un vuelo con la id recibida.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(flight);
    }

    @Override
    public ResponseEntity<?> getFlights(String dateFrom, String dateTo, String origin, String destination) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate dateF = LocalDate.parse(dateFrom, formatter);
        LocalDate dateT = LocalDate.parse(dateTo, formatter);

        System.out.println(dateF);
        System.out.println(dateT);

        // Se buscan los vuelos correspondientes a los parámetros en la base de datos
        List<Flight> flights = repository.findFlights(dateF, dateT, origin, destination).orElse(null);

        if (flights == null) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) No se han encontrado vuelos.");
        }

        // Si no se encuentran vuelos según los parámetros devuelve el status code correspondiente
        if (flights.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No existen vuelos con los parámetros introducidos");
        }

        return ResponseEntity.status(HttpStatus.OK).body(flights);
    }

    @Override
    public ResponseEntity<?> addFlight(FlightDTO flightDTO) {

        Flight newFlight = new Flight();

        newFlight.setFlightNumber(flightDTO.getFlightNumber());
        newFlight.setOrigin(flightDTO.getOrigin());
        newFlight.setDestination(flightDTO.getDestination());
        newFlight.setSeatType(flightDTO.getSeatType());
        newFlight.setFlightPrice(flightDTO.getFlightPrice());
        newFlight.setOutwardDate(flightDTO.getOutwardDate());

        // Se comprueba si existe un vuelo idéntico en la base de datos, si es así, se devuelve el status code correspondiente
        if (flightValidation(newFlight)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("(400) Error al guardar el vuelo. Ya existe un vuelo idéntico en la base de datos.");
        }

        // Si no es idéntico, se guarda el vuelo creado en la base de datos
        repository.save(newFlight);

        return ResponseEntity.status(HttpStatus.CREATED).body("(201) Vuelo creado correctamente.");
    }

    // Método para validar si existe un vuelo en la base de datos
    private boolean flightValidation(Flight flight) {

        Optional<Flight> existingFlight = repository
                .findByDestinationAndFlightNumberAndFlightPriceAndOriginAndOutwardDateAndSeatType(
                        flight.getDestination(),
                        flight.getFlightNumber(),
                        flight.getFlightPrice(),
                        flight.getOrigin(),
                        flight.getOutwardDate(),
                        flight.getSeatType());

        return existingFlight.isPresent();
    }

    @Override
    public ResponseEntity<?> editFlight(long id,
                             String flightNumber,
                             String origin,
                             String destination,
                             String seatType,
                             double flightPrice,
                             String outwardDate) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(outwardDate, formatter);

        // Se busca el vuelo correspondiente a la id en la base de datos
        Flight flight = repository.findById(id).orElse(null);

        // Si el vuelo existe se editan los valores con los parámetros recibidos
        if (flight != null) {

            flight.setFlightNumber(flightNumber);
            flight.setOrigin(origin);
            flight.setDestination(destination);
            flight.setSeatType(seatType);
            flight.setFlightPrice(flightPrice);
            flight.setOutwardDate(date);

            // Se confirman los cambios en la base de datos
            repository.save(flight);

            return ResponseEntity.status(HttpStatus.OK).body(flight);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("(404) No se ha encontrado un vuelo con la id recibida.");
    }

    @Override
    public ResponseEntity<?> deleteFlight(long id) {

        // Se busca el vuelo correspondiente a la id en la base de datos
        Flight flight = repository.findById(id).orElse(null);

        // Si el vuelo es nulo se devuelve el status code correspondiente
        if (flight == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado un vuelo con la id recibida.");
        }

        // Si el vuelo cuenta con reservas activas se devuelve el status code correspondiente
        if (!flight.getReservations().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("(400) No se puede eliminar un vuelo con reservas activas.");
        }

        // Si no se cumple lo anterior se elimina el vuelo de la base de datos
        repository.delete(flight);

        return ResponseEntity.status(HttpStatus.OK).body("(200) Vuelo eliminado correctamente.");
    }
}