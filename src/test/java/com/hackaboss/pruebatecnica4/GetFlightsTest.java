package com.hackaboss.pruebatecnica4;

import com.hackaboss.pruebatecnica4.model.Flight;
import com.hackaboss.pruebatecnica4.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GetFlightsTest {

    @Autowired
    private FlightRepository repository;

    @Test
    public void testGetFlights() {

        List<Flight> flights = repository.findAll();

        assertEquals(12, flights.size());
    }
}
