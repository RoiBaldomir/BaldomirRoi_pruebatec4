package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.HotelReservationDTO;
import com.hackaboss.pruebatecnica4.model.Hotel;
import com.hackaboss.pruebatecnica4.model.HotelReservation;
import com.hackaboss.pruebatecnica4.model.Person;
import com.hackaboss.pruebatecnica4.model.Room;
import com.hackaboss.pruebatecnica4.repository.HotelRepository;
import com.hackaboss.pruebatecnica4.repository.HotelReservationRepository;
import com.hackaboss.pruebatecnica4.repository.PersonRepository;
import com.hackaboss.pruebatecnica4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class HotelReservationService implements IHotelReservationService{

    @Autowired
    private HotelReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> addHotelReservation(HotelReservationDTO hotelReservationDTO) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate dateFrom = LocalDate.parse(hotelReservationDTO.getDateFrom(), formatter);
        LocalDate dateTo = LocalDate.parse(hotelReservationDTO.getDateTo(), formatter);

        // Se busca el hotel correspondiente a la reserva en la base de datos
        Hotel hotel = hotelRepository.findByHotelCode(hotelReservationDTO.getHotelCode()).orElse(null);
        Room room = roomRepository.findByHotel(hotel).orElse(null);

        // Si no se encuentra el hotel se devuelve el status code correspondiente
        if (room == null || hotel == null) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) Habitación o hotel no encontrado.");
        }

        // Se añaden los datos a la reserva
        HotelReservation reservation = getReservation(dateFrom, dateTo, hotelReservationDTO, room);

        // Se comprueba si existe una reserva idéntica en la base de datos, si es así, se devuelve el status code correspondiente
        if (reservationExistsValidation(reservation)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("(400) Error al guardar la reserva. Ya existe una reserva idéntica en la base de datos.");
        }

        // Se cambia el estado de la habitación correspondiente a la reserva
        if (dateFrom.isEqual(room.getDateFrom()) || (dateFrom.isAfter(room.getDateFrom()) && dateFrom.isBefore(room.getDateTo()))) {

            room.setBooked(true);
        }

        // Si no es idéntica se guarda la reserva en la base de datos
        roomRepository.save(room);
        personRepository.saveAll(hotelReservationDTO.getHosts());
        reservationRepository.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelReservationDTO.getPeopleQ() * hotelReservationDTO.getNights() * room.getRoomPrice());
    }

    // Método para comprobar si la reserva es idéntica
    private boolean reservationExistsValidation(HotelReservation hotelReservation) {

        Optional<HotelReservation> existingReservation = reservationRepository
                .findByDateFromAndDateToAndNightsAndPlaceAndHotelCodeAndPeopleQAndRoomType(hotelReservation.getDateFrom(),
                        hotelReservation.getDateTo(),
                        hotelReservation.getNights(),
                        hotelReservation.getPlace(),
                        hotelReservation.getHotelCode(),
                        hotelReservation.getPeopleQ(),
                        hotelReservation.getRoomType());

        return existingReservation.isPresent();
    }

    // Método para añadir los datos a la reserva
    private HotelReservation getReservation(LocalDate dateFrom,
                                            LocalDate dateTo,
                                            HotelReservationDTO hotelReservationDTO,
                                            Room room) {

        HotelReservation reservation = new HotelReservation();

        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setNights(hotelReservationDTO.getNights());
        reservation.setPlace(hotelReservationDTO.getPlace());
        reservation.setHotelCode(hotelReservationDTO.getHotelCode());
        reservation.setPeopleQ(hotelReservationDTO.getPeopleQ());
        reservation.setRoomType(hotelReservationDTO.getRoomType());
        reservation.setHotelBooker(hotelReservationDTO.getHosts().get(0).getName() +
                " " + hotelReservationDTO.getHosts().get(0).getSurname());
        reservation.setTotalPrice(hotelReservationDTO.getPeopleQ() * hotelReservationDTO.getNights() * room.getRoomPrice());
        reservation.setRoomData(room);
        reservation.setHosts(hotelReservationDTO.getHosts());

        return reservation;
    }

    @Override
    public ResponseEntity<?> getHotelReservations() {

        // Si se recibe una lista vacía se devuelve el status code correspondiente
        if (reservationRepository.findAll().isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("(404) No se han encontrado reservas de hoteles.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(reservationRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getHotelReservation(long id) {

        HotelReservation hotelReservation = reservationRepository.findById(id).orElse(null);

        // Si la reserva es nula se devuelve el status code correspondiente
        if (hotelReservation == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("(404) No se ha encontrado una reserva con la id recibida.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(reservationRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> editHotelReservation(long id,
                                                 String dateFrom,
                                                 String dateTo,
                                                 int nights,
                                                 String place,
                                                 String hotelCode,
                                                 int peopleQ,
                                                 String roomType,
                                                 double totalPrice) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate dateF = LocalDate.parse(dateFrom, formatter);
        LocalDate dateT = LocalDate.parse(dateTo, formatter);

        // Se busca la reserva correspondiente a la id en la base de datos
        HotelReservation hotelReservation = reservationRepository.findById(id).orElse(null);
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElse(null);
        Room room = roomRepository.findByHotel(hotel).orElse(null);

        // Si la reserva existe se editan los valores con los parámetros recibidos
        if (hotelReservation != null) {

            // Se quita la reserva del anterior hotel
            Room oldRoom = roomRepository.findById(hotelReservation.getRoomData().getId()).orElse(null);

            if (oldRoom != null) {

                oldRoom.setBooked(false);
                roomRepository.save(oldRoom);
            }else {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) Habitación no encontrada.");
            }

            hotelReservation.setDateFrom(dateF);
            hotelReservation.setDateTo(dateT);
            hotelReservation.setNights(nights);
            hotelReservation.setPlace(place);
            hotelReservation.setHotelCode(hotelCode);
            hotelReservation.setPeopleQ(peopleQ);
            hotelReservation.setRoomType(roomType);
            hotelReservation.setTotalPrice(totalPrice);
            hotelReservation.setRoomData(room);

            // Se confirman los cambios en la base de datos
            if (room != null) {

                room.setBooked(true);
                roomRepository.save(room);
            }else {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) Habitación no encontrada.");
            }

            reservationRepository.save(hotelReservation);

            return ResponseEntity.status(HttpStatus.OK).body(hotelReservation);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("(404) No se ha encontrado una reserva con la id recibida.");
    }

    @Override
    public ResponseEntity<String> deleteHotelReservation(long id) {

        // Se busca la reserva correspondiente a la id en la base de datos
        HotelReservation hotelReservation = reservationRepository.findById(id).orElse(null);

        // Si la reserva es nula se devuelve el status code correspondiente
        if (hotelReservation == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado una reserva con la id recibida.");
        }

        // Se eliminan las relaciones entre Persona y Reserva para permitir la eliminación de ambos
        for (Person person : hotelReservation.getHosts()) {

            person.getHotelReservations().remove(hotelReservation);
            personRepository.save(person);
        }

        // Se trae la habitación reservada
        Hotel hotel = hotelRepository.findByHotelCode(hotelReservation.getHotelCode()).orElse(null);
        Room room = roomRepository.findByHotel(hotel).orElse(null);

        if (room != null) {

            // Se cambia el estado de la habitación y se guarda en la base de datos
            room.setBooked(false);
            roomRepository.save(room);
        }else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) Habitación no encontrada.");
        }

        // Se elimina la reserva de la base de datos
        personRepository.deleteAll(hotelReservation.getHosts());
        reservationRepository.delete(hotelReservation);

        return ResponseEntity.status(HttpStatus.OK).body("(200) Reserva eliminada correctamente.");
    }
}
