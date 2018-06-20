package com.uraltranscom.service;

import java.util.List;

/**
 *
 * Интерфейс подбора станции возврата
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 24.05.2018
 *
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public interface GetReturnStation {
    /**
     *
     * @param keyOfStation - Код станции назначения второго рейса
     * @param volume - Объем вагона
     * @return - Возвращает список (код станции возврата, дорога, название станции)
     */
    List<String> getReturnStation(String keyOfStation, int volume);
}

