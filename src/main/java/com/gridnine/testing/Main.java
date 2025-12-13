package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.impl.DepartureBeforeNowFilter;
import com.gridnine.testing.service.impl.ExcessiveGroundTimeFilter;
import com.gridnine.testing.util.FlightBuilder;
import com.gridnine.testing.util.FlightPrinter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterService filterService = new FlightFilterService();

        FlightPrinter.printFlights("Первоначальный набор полетов:", flights);
        demonstrateFilters(flights, filterService);
    }

    private static void demonstrateFilters(List<Flight> flights, FlightFilterService filterService) {
        // Фильтр 1: вылет до текущего момента времени
        List<Flight> filtered1 = filterService.filter(flights, new DepartureBeforeNowFilter());
        FlightPrinter.printFlights("Удалены перелёты с вылетом до текущего момента:", filtered1);

        // Фильтр 2: сегменты с датой прилёта раньше даты вылета
        List<Flight> filtered2 = filterService.filter(flights, new ArrivalBeforeDepartureFilter());
        FlightPrinter.printFlights("Удалены перелёты с сегментами, где прилёт раньше вылета:", filtered2);

        // Фильтр 3: общее время на земле превышает два часа
        List<Flight> filtered3 = filterService.filter(flights, new ExcessiveGroundTimeFilter());
        FlightPrinter.printFlights("Удалены перелёты с общим временем на земле более 2 часов:", filtered3);
    }
}
