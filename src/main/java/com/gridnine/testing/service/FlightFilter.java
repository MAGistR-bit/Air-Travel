package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

/**
 * @author Mikhail
 * <p>Интерфейс для правил фильтрации перелетов.</p>
 */
@FunctionalInterface
public interface FlightFilter {

    /**
     * Проверяет, должен ли перелет быть исключён из результата.
     * @param flight перелет для проверки
     * @return true, если перелет должен быть исключен. В противном случае - false.
     */
    boolean shouldExclude(Flight flight);
}
