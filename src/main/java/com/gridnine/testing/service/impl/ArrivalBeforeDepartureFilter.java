package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilter;

/** @author Mikhail
 * <p>Исключает перелёты, имеющие сегменты с датой прилёта раньше даты вылета.</p>
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {

    @Override
    public boolean shouldExclude(Flight flight) {
        return flight.getSegments()
                .stream()
                .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
