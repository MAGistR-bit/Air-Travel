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

class ExcessiveGroundTimeFilterTest {

    @DisplayName(value = "Should not exclude flight, when use flight with a single segment.")
    @Test
    void shouldNotExcludeFlightWhenUseFlightWithSingleSegment() {
        LocalDateTime now = LocalDateTime.now();
        Flight singleSegmentFlight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3))
        ));

        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();
        assertFalse(filter.shouldExclude(singleSegmentFlight));
    }

    @DisplayName(value = "Should not exclude flight, when ground time less than two hours.")
    @Test
    void shouldNotExcludeFlightWhenGroundTimeLessThanTwoHours() {
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3)),
                new Segment(now.plusHours(4), now.plusHours(6)) // 1 час на земле
        ));

        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();
        assertFalse(filter.shouldExclude(flight));
    }

    @DisplayName(value = "Should not exclude flight, when ground time exactly two hours.")
    @Test
    void shouldNotExcludeFlightWhenGroundTimeExactlyTwoHours() {
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3)),
                new Segment(now.plusHours(5), now.plusHours(7)) // ровно 2 часа на земле
        ));

        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();
        assertFalse(filter.shouldExclude(flight));
    }

    @DisplayName(value = "Should exclude flight, when multiple segments exceeding total ground time.")
    @Test
    void shouldExcludeFlightWhenMultipleSegmentsExceedingTotalGroundTime() {
        LocalDateTime now = LocalDateTime.now();
        // Сегмент 1: 1:00 - 3:00
        // Сегмент 2: 4:00 - 5:00 (1 час на земле)
        // Сегмент 3: 8:00 - 9:00 (3 часа на земле)
        // Таким образом: 4 часа на земле
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3)),
                new Segment(now.plusHours(4), now.plusHours(5)),
                new Segment(now.plusHours(8), now.plusHours(9))
        ));

        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();
        assertTrue(filter.shouldExclude(flight));
    }

    @DisplayName(value = "Should return false and true, when test with real flight builder.")
    @Test
    void shouldReturnFalseAndTrueWhenTestWithRealFlightBuilder() {
        List<Flight> flights = FlightBuilder.createFlights();
        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();

        // Пятый перелёт имеет более 2 часов на земле (3 часа между сегментами)
        assertTrue(filter.shouldExclude(flights.get(4)));
        // Шестой перелёт также имеет более 2 часов на земле (суммарно)
        assertTrue(filter.shouldExclude(flights.get(5)));
        // Первые перелёты не должны исключаться
        assertFalse(filter.shouldExclude(flights.get(0)));
        assertFalse(filter.shouldExclude(flights.get(1)));
    }

    @DisplayName(value = "Should not exclude flight, when multiple segments within limit.")
    @Test
    void shouldNotExcludeFlightWithMultipleSegmentsWithinLimit() {
        LocalDateTime now = LocalDateTime.now();
        // Сегмент 1: 1:00 - 3:00
        // Сегмент 2: 4:00 - 5:00 (1 час на земле)
        // Сегмент 3: 6:00 - 7:00 (1 час на земле)
        // Таким образом: 2 часа на земле
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(3)),
                new Segment(now.plusHours(4), now.plusHours(5)),
                new Segment(now.plusHours(6), now.plusHours(7))
        ));

        ExcessiveGroundTimeFilter filter = new ExcessiveGroundTimeFilter();
        assertFalse(filter.shouldExclude(flight));
    }
}
