package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.FlightReservationDTO;
import com.hackaboss.pruebatecnica4.model.Flight;
import com.hackaboss.pruebatecnica4.model.FlightReservation;
import com.hackaboss.pruebatecnica4.model.Person;
import com.hackaboss.pruebatecnica4.repository.FlightRepository;
import com.hackaboss.pruebatecnica4.repository.FlightReservationRepository;
import com.hackaboss.pruebatecnica4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class FlightReservationService implements IFlightReservationService {

    @Autowired
    private FlightReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public ResponseEntity<?> addFlightReservation(FlightReservationDTO flightReservationDTO) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(flightReservationDTO.getDate(), formatter);

        // Se busca el vuelo correspondiente a la reserva en la base de datos
        Flight flight = flightRepository.findAll().stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightReservationDTO.getFlightCode()) &&
                        f.getSeatType().equalsIgnoreCase(flightReservationDTO.getSeatType()))
                .findFirst()
                .orElse(null);

        // Si no se encuentra el vuelo se devuelve el status code correspondiente
        if (flight == null) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) Vuelo no encontrado.");
        }

        // Se añaden los datos a la reserva
        FlightReservation reservation = getReservation(flightReservationDTO, date, flight);

        // Se comprueba si existe una reserva idéntica en la base de datos, si es así, se devuelve el status code correspondiente
        if (reservationExistsValidation(reservation)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("(400) Error al guardar la reserva. Ya existe una reserva idéntica en la base de datos.");
        } else {

            // Si no es idéntica se guarda la reserva en la base de datos
            personRepository.saveAll(flightReservationDTO.getPassengers());
            reservationRepository.save(reservation);

            return ResponseEntity.status(HttpStatus.OK).body(flightReservationDTO.getPeopleQ() * flight.getFlightPrice());
        }
    }

    // Método para comprobar si la reserva es idéntica
    private boolean reservationExistsValidation(FlightReservation flightReservation) {

        Optional<FlightReservation> existingReservation = reservationRepository
                .findByDateAndOriginAndDestinationAndFlightCodeAndPeopleQAndSeatType(
                        flightReservation.getDate(),
                        flightReservation.getOrigin(),
                        flightReservation.getDestination(),
                        flightReservation.getFlightCode(),
                        flightReservation.getPeopleQ(),
                        flightReservation.getSeatType());

        return existingReservation.isPresent();
    }

    // Método para añadir los datos a la reserva
    private FlightReservation getReservation(FlightReservationDTO flightReservationDTO, LocalDate date, Flight flight) {

        FlightReservation reservation = new FlightReservation();

        reservation.setDate(date);
        reservation.setOrigin(flightReservationDTO.getOrigin());
        reservation.setDestination(flightReservationDTO.getDestination());
        reservation.setFlightCode(flightReservationDTO.getFlightCode());
        reservation.setSeatType(flightReservationDTO.getSeatType());
        reservation.setFlightBooker(flightReservationDTO.getPassengers().get(0).getName() +
                " " + flightReservationDTO.getPassengers().get(0).getSurname());
        reservation.setPeopleQ(flightReservationDTO.getPeopleQ());
        reservation.setFlightData(flight);
        reservation.setPassengers(flightReservationDTO.getPassengers());
        reservation.setTotalPrice(flightReservationDTO.getPeopleQ() * flight.getFlightPrice());

        return reservation;
    }

    @Override
    public ResponseEntity<?> getFlightReservations() {

        // Si se recibe una lista vacía se devuelve el status code correspondiente
        if (reservationRepository.findAll().isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("(404) No se han encontrado reservas de vuelos.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(reservationRepository.findAll());
    }

    @Override
    public ResponseEntity<?> editFlightReservation(long id,
                                                   String outwardDate,
                                                   String origin,
                                                   String destination,
                                                   String flightCode,
                                                   int peopleQ,
                                                   String seatType,
                                                   String flightBooker,
                                                   double totalPrice) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(outwardDate, formatter);

        // Se busca la reserva correspondiente a la id en la base de datos
        FlightReservation flightReservation = reservationRepository.findById(id).orElse(null);

        // Si la reserva existe se editan los valores con los parámetros recibidos
        if (flightReservation != null) {

            flightReservation.setDate(date);
            flightReservation.setOrigin(origin);
            flightReservation.setDestination(destination);
            flightReservation.setFlightCode(flightCode);
            flightReservation.setPeopleQ(peopleQ);
            flightReservation.setSeatType(seatType);
            flightReservation.setFlightBooker(flightBooker);
            flightReservation.setTotalPrice(totalPrice);

            // Se confirman los cambios en la base de datos
            reservationRepository.save(flightReservation);

            return ResponseEntity.status(HttpStatus.OK).body(flightReservation);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("(404) No se ha encontrado una reserva con la id recibida.");
    }

    @Override
    public ResponseEntity<?> deleteFlightReservation(long id) {

        // Se busca la reserva correspondiente a la id en la base de datos
        FlightReservation flightReservation = reservationRepository.findById(id).orElse(null);

        // Si la reserva es nula se devuelve el status code correspondiente
        if (flightReservation == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado una reserva con la id recibida.");
        }

        // Se eliminan las relaciones entre Persona y Reserva para permitir la eliminación de ambos
        for (Person person : flightReservation.getPassengers()) {

            person.getFlightReservations().remove(flightReservation);
            personRepository.save(person);
        }

        // Se elimina la reserva de la base de datos
        personRepository.deleteAll(flightReservation.getPassengers());
        reservationRepository.delete(flightReservation);

        return ResponseEntity.status(HttpStatus.OK).body("(200) Reserva eliminada correctamente.");
    }
}
