package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.util.FlightBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DepartureBeforeNowFilterTest {

    @DisplayName(value = "Should exclude flight, when departure date in past.")
    @Test
    void shouldExcludeFlightWhenDepartureInPast() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusDays(1);
        LocalDateTime future = now.plusDays(1);

        Flight pastFlight = new Flight(List.of(
                new Segment(past, future)
        ));

        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter(now);
        assertTrue(filter.shouldExclude(pastFlight));
    }

    @DisplayName(value = "Should not exclude flight, when departure date in future.")
    @Test
    void shouldNotExcludeFlightWhenDepartureInFuture() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future1 = now.plusDays(1);
        LocalDateTime future2 = now.plusDays(2);

        Flight futureFlight = new Flight(List.of(
                new Segment(future1, future2)
        ));

        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter(now);
        assertFalse(filter.shouldExclude(futureFlight));
    }

    @DisplayName(value = "Should return true and false, when use a real flight builder.")
    @Test
    void shouldReturnTrueAndFalseWhenUseRealFlightBuilder() {
        List<Flight> flights = FlightBuilder.createFlights();
        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();

        // Третий перелёт в списке имеет вылет в прошлом
        assertTrue(filter.shouldExclude(flights.get(2)));

        // Остальные перелёты не должны исключаться
        assertFalse(filter.shouldExclude(flights.get(0)));
        assertFalse(filter.shouldExclude(flights.get(1)));
    }
}
