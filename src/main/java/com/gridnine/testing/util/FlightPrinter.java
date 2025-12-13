package com.gridnine.testing.util;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * @author Mikhail
 * <p>Утилита для вывода информации о перелетах в консоль.</p>
 */
public class FlightPrinter {

    /**
     * Выводит список перелетов в консоль.
     * @param flights список перелетов для вывода
     */
    public static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("(нет перелётов)");
        } else {
            flights.forEach(flight -> System.out.println("  " + flight));
        }
    }

    /**
     * Выводит список перелётов с заголовком.
     * @param title заголовок
     * @param flights список перелётов
     */
    public static void printFlights(String title, List<Flight> flights) {
        System.out.println(title);
        printFlights(flights);
        System.out.println();
    }
}
