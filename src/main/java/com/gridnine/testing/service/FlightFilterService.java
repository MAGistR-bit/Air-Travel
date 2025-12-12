package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * @author Mikhail
 * <p>Сервис для фильтрации списка перелетов.</p>
 */
public class FlightFilterService {

    /**
     * Фильтрует список перелетов, исключая те, которые соответствуют правилам.
     * @param flights первоначальный список перелетов
     * @param filter правила фильтрации
     * @return отфильтрованный список перелетов
     */
    public List<Flight> filter(List<Flight> flights, FlightFilter filter) {
        return flights.stream()
                .filter(flight -> !filter.shouldExclude(flight))
                .toList();
    }
}
