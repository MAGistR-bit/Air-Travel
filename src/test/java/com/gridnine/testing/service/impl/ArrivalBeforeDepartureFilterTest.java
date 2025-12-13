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

class ArrivalBeforeDepartureFilterTest {

    @DisplayName(value = "Should exclude flight, when arrival date before departure date.")
    @Test
    void shouldExcludeFlightWhenArrivalDateBeforeDepartureDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = now.plusHours(2);
        LocalDateTime arrival = now.plusHours(1); // прилёт раньше вылета

        Flight invalidFlight = new Flight(List.of(
                new Segment(departure, arrival)
        ));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        assertTrue(filter.shouldExclude(invalidFlight));
    }

    @DisplayName(value = "Should not exclude flight, when flight have a normal segments.")
    @Test
    void shouldNotExcludeFlightWhenFlightHaveNormalSegments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure1 = now.plusHours(1);
        LocalDateTime arrival1 = now.plusHours(3);

        LocalDateTime departure2 = now.plusHours(4);
        LocalDateTime arrival2 = now.plusHours(6);

        Flight validFlight = new Flight(List.of(
                new Segment(departure1, arrival1),
                new Segment(departure2, arrival2)
        ));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        assertFalse(filter.shouldExclude(validFlight));
    }

    @DisplayName(value = "Should not exclude flight, when arrival date equals departure date.")
    @Test
    void shouldNotExcludeWhenArrivalDateEqualsDepartureDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sameTime = now.plusHours(1);

        Flight flightWithSameTime = new Flight(List.of(
                new Segment(sameTime, sameTime)
        ));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        // Прилёт не раньше вылета (равен), поэтому не должен исключаться
        assertFalse(filter.shouldExclude(flightWithSameTime));
    }

    @DisplayName(value = "Should correct validate flights, when use a real flight builder.")
    @Test
    void shouldCorrectValidateFlightsWhenUseRealFlightBuilder() {
        List<Flight> flights = FlightBuilder.createFlights();
        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();

        // Четвёртый перелёт в списке имеет сегмент с прилётом раньше вылета
        assertTrue(filter.shouldExclude(flights.get(3)));
        // Остальные перелёты не должны исключаться
        assertFalse(filter.shouldExclude(flights.get(0)));
        assertFalse(filter.shouldExclude(flights.get(1)));
    }

    @DisplayName(value = "Should exclude flight with multiple segments, "
            + "when one segment has arrival before departure.")
    @Test
    void shouldExcludeFlightWithMultipleSegmentsWhenOneIsInvalid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure1 = now.plusHours(1);
        LocalDateTime arrival1 = now.plusHours(3);

        // Прилет раньше вылета
        LocalDateTime departure2 = now.plusHours(5);
        LocalDateTime arrival2 = now.plusHours(4);

        Flight flightWithInvalidSegment = new Flight(List.of(
                new Segment(departure1, arrival1),
                new Segment(departure2, arrival2)
        ));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        assertTrue(filter.shouldExclude(flightWithInvalidSegment));
    }
}
