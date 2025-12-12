package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilter;

import java.time.temporal.ChronoUnit;
import java.util.List;

/** @author Mikhail
 * <p>Исключает перелёты, где общее время на земле превышает два часа.</p>
 */
public class ExcessiveGroundTimeFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_HOURS = 2;


    @Override
    public boolean shouldExclude(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() < 2) {
            return false;           // нет пересадок
        }

        long totalGroundTime = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            Segment current = segments.get(i);
            Segment next = segments.get(i + 1);

            long groundTime = ChronoUnit.HOURS.between(current.getArrivalDate(),
                    next.getDepartureDate());

            totalGroundTime += groundTime;
        }

        return totalGroundTime > MAX_GROUND_TIME_HOURS;
    }
}
