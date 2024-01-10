package com.hackaboss.pruebatecnica4.repository;

import com.hackaboss.pruebatecnica4.model.Hotel;
import com.hackaboss.pruebatecnica4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByHotel(Hotel hotel);
}
