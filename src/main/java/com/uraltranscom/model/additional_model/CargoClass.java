package com.uraltranscom.model.additional_model;

import com.uraltranscom.util.PropertyUtil;

import java.util.Objects;

/**
 *
 * Класс Груза
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 15.06.2018
 *
 * 15.06.2018
 *   1. Версия 5.0
 *
 */

public class CargoClass {

    private String nameCargo; // Груз
    private String keyCargo; // Код груза
    private boolean isNeedWashingStation; // Флаг, необходима ли станция промывки

    public CargoClass(String nameCargo, String keyCargo) {
        this.nameCargo = nameCargo;
        this.keyCargo = keyCargo;
        this.isNeedWashingStation = getFlagNeedWashingStation(keyCargo);
    }

    public String getNameCargo() {
        return nameCargo;
    }

    public void setNameCargo(String nameCargo) {
        this.nameCargo = nameCargo;
    }

    public String getKeyCargo() {
        return keyCargo;
    }

    public void setKeyCargo(String keyCargo) {
        this.keyCargo = keyCargo;
    }

    public boolean isNeedWashingStation() {
        return isNeedWashingStation;
    }

    public void setNeedWashingStation(boolean needWashingStation) {
        isNeedWashingStation = needWashingStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoClass that = (CargoClass) o;
        return isNeedWashingStation == that.isNeedWashingStation &&
                Objects.equals(nameCargo, that.nameCargo) &&
                Objects.equals(keyCargo, that.keyCargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameCargo, keyCargo, isNeedWashingStation);
    }

    /**
     *
     * @param keyCargo - код текущего груза
     * @return - возвращает флаг, нужна ли станция промывки после груза
     */

    private boolean getFlagNeedWashingStation(String keyCargo) {
        PropertyUtil propertyUtil = new PropertyUtil();
        String[] keyCargoWashing = propertyUtil.getProperty("cargo.washingkey").split(";");
        for (String _keyCargoWashing : keyCargoWashing) {
            if (_keyCargoWashing.equals(keyCargo)) return true;
        }
        return false;
    }
}
