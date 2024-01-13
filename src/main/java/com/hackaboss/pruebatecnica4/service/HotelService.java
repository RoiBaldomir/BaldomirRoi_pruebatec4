package com.hackaboss.pruebatecnica4.service;

import com.hackaboss.pruebatecnica4.dto.HotelDTO;
import com.hackaboss.pruebatecnica4.model.Hotel;
import com.hackaboss.pruebatecnica4.model.Room;
import com.hackaboss.pruebatecnica4.repository.HotelRepository;
import com.hackaboss.pruebatecnica4.repository.RoomRepository;
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
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> getHotels() {

        // Si se recibe una lista vacía se devuelve el status code correspondiente
        if (hotelRepository.findAll().isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("(404) No se han encontrado hoteles.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(hotelRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getHotels(String dateFrom, String dateTo, String place) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate df = LocalDate.parse(dateFrom, formatter);
        LocalDate dt = LocalDate.parse(dateTo, formatter);

        boolean isBooked = false;

        // Se buscan los hoteles correspondientes a los parámetros en la base de datos
        List<Hotel> hotels = hotelRepository.getHotels(df, dt, place, isBooked).orElse(null);

        if (hotels == null) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("(500) No se han encontrado hoteles.");
        }

        // Si no se encuentran hoteles según los parámetros devuelve el status code correspondiente
        if (hotels.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No existen hoteles con los parámetros introducidos.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @Override
    public ResponseEntity<?> getHotel(long id) {

        // Se busca el hotel correspondiente a la id en la base de datos
        Hotel hotel = hotelRepository.findById(id).orElse(null);

        // Si el hotel es nulo se devuelve el status code correspondiente
        if (hotel == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado un hotel con la id recibida.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @Override
    public ResponseEntity<String> addHotel(HotelDTO hotelDTO) {

        Hotel newHotel = new Hotel();
        Room room = new Room();

        newHotel.setHotelCode(hotelDTO.getHotelCode());
        newHotel.setHotelName(hotelDTO.getName());
        newHotel.setHotelPlace(hotelDTO.getPlace());

        // Se comprueba si existe un hotel idéntico en la base de datos, si es así, se devuelve el status code correspondiente
        if (hotelValidation(newHotel)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("(400) Error al guardar el hotel. Ya existe un hotel idéntico en la base de datos.");
        }

        room.setDateFrom(hotelDTO.getDateFrom());
        room.setDateTo(hotelDTO.getDateTo());
        room.setBooked(hotelDTO.isBooked());
        room.setRoomPrice(hotelDTO.getRoomPrice());
        room.setRoomType(hotelDTO.getRoomType());
        room.setHotel(newHotel);

        // Se guarda el hotel creado en la base de datos
        hotelRepository.save(newHotel);
        roomRepository.save(room);

        return ResponseEntity.status(HttpStatus.CREATED).body("(201) Hotel creado correctamente.");
    }

    // Método para validar si existe un hotel en la base de datos
    private boolean hotelValidation(Hotel hotel) {

        Optional<Hotel> existingHotel = hotelRepository
                .findByHotelCodeAndHotelNameAndHotelPlace(hotel.getHotelCode(),
                        hotel.getHotelName(),
                        hotel.getHotelPlace());

        return existingHotel.isPresent();
    }

    @Override
    public ResponseEntity<?> editHotel(long id,
                           String hotelCode,
                           String hotelName,
                           String hotelPlace,
                           String roomType,
                           double roomPrice,
                           String dateTo,
                           String dateFrom,
                           boolean isBooked) {

        // Se parsean las fechas de String a LocalDate con el formato de la base de datos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date1 = LocalDate.parse(dateTo, formatter);
        LocalDate date2 = LocalDate.parse(dateFrom, formatter);

        // Se busca el hotel correspondiente a la id en la base de datos
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        Room room = roomRepository.findByHotel(hotel).orElse(null);

        // Si el hotel existe se editan los valores con los parámetros recibidos
        if (hotel != null && room != null) {

            hotel.setHotelCode(hotelCode);
            hotel.setHotelName(hotelName);
            hotel.setHotelPlace(hotelPlace);

            room.setDateTo(date1);
            room.setDateFrom(date2);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setBooked(isBooked);

            // Se confirman los cambios en la base de datos
            hotelRepository.save(hotel);
            roomRepository.save(room);

            return ResponseEntity.status(HttpStatus.OK).body(hotel);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado un hotel con la id recibida.");
        }

    @Override
    public ResponseEntity<String> deleteHotel(long id) {

        // Se busca el hotel correspondiente a la id en la base de datos
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        Room room = roomRepository.findByHotel(hotel).orElse(null);

        // Si el hotel es nulo se devuelve el status code correspondiente
        if (hotel == null || room == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(404) No se ha encontrado un hotel con la id recibida.");
        }

        // Si el hotel cuenta con reservas activas se devuelve el status code correspondiente
        if (!room.getReservations().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("(400) No se puede eliminar un hotel con reservas activas.");
        }

        // Si no se cumple lo anterior se elimina el hotel de la base de datos
        roomRepository.delete(room);
        hotelRepository.delete(hotel);

        return ResponseEntity.status(HttpStatus.OK).body("(200) Hotel eliminado correctamente.");
    }
}
