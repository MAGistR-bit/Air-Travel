package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilter;

import java.time.LocalDateTime;

/**
 * @author Mikhail
 * <p>Исключает перелеты, вылет которых уже произошел.</p>
 */
public class DepartureBeforeNowFilter implements FlightFilter {
    private final LocalDateTime now;

    public DepartureBeforeNowFilter() {
        this(LocalDateTime.now());
    }

    public DepartureBeforeNowFilter(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public boolean shouldExclude(Flight flight) {
        if (flight.getSegments().isEmpty()) {
            return false;
        }
        LocalDateTime firstDeparture = flight.getSegments().getFirst().getDepartureDate();
        return firstDeparture.isBefore(now);
    }
}
