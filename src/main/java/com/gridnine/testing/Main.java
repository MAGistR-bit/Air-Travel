package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.impl.DepartureBeforeNowFilter;
import com.gridnine.testing.service.impl.ExcessiveGroundTimeFilter;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterService filterService = new FlightFilterService();

        System.out.println("Первоначальный набор полетов:");
        printFlights(flights);
        System.out.println();

        // Фильтр 1: вылет до текущего момента времени
        List<Flight> filtered1 = filterService.filter(flights, new DepartureBeforeNowFilter());
        System.out.println("Удалены перелёты с вылетом до текущего момента:");
        printFlights(filtered1);
        System.out.println();

        // Фильтр 2: сегменты с датой прилёта раньше даты вылета
        List<Flight> filtered2 = filterService.filter(flights, new ArrivalBeforeDepartureFilter());
        System.out.println("Удалены перелёты с сегментами, где прилёт раньше вылета:");
        printFlights(filtered2);
        System.out.println();

        // Фильтр 3: общее время на земле превышает два часа
        List<Flight> filtered3 = filterService.filter(flights, new ExcessiveGroundTimeFilter());
        System.out.println("Удалены перелёты с общим временем на земле более 2 часов:");
        printFlights(filtered3);
    }

    private static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("(нет перелётов)");
        } else {
            flights.forEach(flight -> System.out.println("  " + flight));
        }
    }
}
