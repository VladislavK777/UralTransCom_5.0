package com.uraltranscom.model_ext;

import java.util.Objects;

/**
 *
 * Класс для формирвоания итоговой информации по расчету
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 06.06.2018
 *
 * 06.06.2018
 *   1. Версия 5.0
 *
 */

public class TotalCalculateRoute {

    // Параметры текущейго положения вагона
    private String currentNameStationDeparture;
    private String currentNameStationDestination;
    private String currentCargo;
    private String currentDistance;
    private double currentRate;
    private double currentCountDays;
    private double currentCountDaysMinLoad;

    // Параметры порожнего рейса
    private String emptyNameStationDeparture;
    private String emptyNameStationDestination;
    private String emptyDistance;
    private String emptyCargo = "порожняк";
    private double emptyTariff;
    private double emptyCountDays;
    private double emptyCountDaysMinLoad;

    // Парметры второго рейса
    private String secondNameStationDeparture;
    private String secondNameStationDestination;
    private String secondCargo;
    private String secondDistance;
    private double secondRate;
    private double secondCountDays;
    private double secondCountDaysMinLoad;

    // Параметры второго порожнего рейса
    private String emptySecondNameStationDeparture;
    private String emptySecondStationDestination;
    private String emptySecondDistance;
    private String emptySecondCargo = "порожняк";
    private double emptySecondTariff;
    private double emptySecondCountDays;
    private double emptySecondCountDaysMinLoad;

    // Итоговые суммы
    private int distanceSummary;
    private double countDaysSummary;
    private double countDaysSummaryMinLoad;
    private double totalSummary;

    public TotalCalculateRoute(String currentNameStationDeparture, String currentNameStationDestination, String currentCargo, String currentDistance, double currentRate, double currentCountDays, String emptyNameStationDeparture, String emptyNameStationDestination, String emptyDistance, double emptyTariff, double emptyCountDays, String secondNameStationDeparture, String secondNameStationDestination, String secondCargo, String secondDistance, double secondRate, double secondCountDays, String emptySecondNameStationDeparture, String emptySecondStationDestination, String emptySecondDistance, double emptySecondTariff, double emptySecondCountDays) {
        this.currentNameStationDeparture = currentNameStationDeparture;
        this.currentNameStationDestination = currentNameStationDestination;
        this.currentCargo = currentCargo;
        this.currentDistance = currentDistance;
        this.currentRate = currentRate;
        this.currentCountDays = currentCountDays;
        this.emptyNameStationDeparture = emptyNameStationDeparture;
        this.emptyNameStationDestination = emptyNameStationDestination;
        this.emptyDistance = emptyDistance;
        this.emptyTariff = emptyTariff;
        this.emptyCountDays = emptyCountDays;
        this.secondNameStationDeparture = secondNameStationDeparture;
        this.secondNameStationDestination = secondNameStationDestination;
        this.secondCargo = secondCargo;
        this.secondDistance = secondDistance;
        this.secondRate = secondRate;
        this.secondCountDays = secondCountDays;
        this.emptySecondNameStationDeparture = emptySecondNameStationDeparture;
        this.emptySecondStationDestination = emptySecondStationDestination;
        this.emptySecondDistance = emptySecondDistance;
        this.emptySecondTariff = emptySecondTariff;
        this.emptySecondCountDays = emptySecondCountDays;
        this.totalSummary = currentRate - emptyTariff + secondRate - emptySecondTariff;
        this.currentCountDaysMinLoad = currentCountDays + 7;
        this.emptyCountDaysMinLoad = emptyCountDays + 4;
        this.secondCountDaysMinLoad = secondCountDays + 10;
        this.emptySecondCountDaysMinLoad = emptyCountDays + 4;
        this.distanceSummary = Integer.parseInt(currentDistance) + Integer.parseInt(emptyDistance) + Integer.parseInt(secondDistance) + Integer.parseInt(emptySecondDistance);
        this.countDaysSummary = currentCountDays + emptyCountDays + secondCountDays + emptySecondCountDays;
        this.countDaysSummaryMinLoad = this.currentCountDaysMinLoad + this.emptyCountDaysMinLoad + this.secondCountDaysMinLoad + this.emptySecondCountDaysMinLoad;
    }

    public String getCurrentNameStationDeparture() {
        return currentNameStationDeparture;
    }

    public void setCurrentNameStationDeparture(String currentNameStationDeparture) {
        this.currentNameStationDeparture = currentNameStationDeparture;
    }

    public String getCurrentNameStationDestination() {
        return currentNameStationDestination;
    }

    public void setCurrentNameStationDestination(String currentNameStationDestination) {
        this.currentNameStationDestination = currentNameStationDestination;
    }

    public String getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(String currentCargo) {
        this.currentCargo = currentCargo;
    }

    public String getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(String currentDistance) {
        this.currentDistance = currentDistance;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(double currentRate) {
        this.currentRate = currentRate;
    }

    public double getCurrentCountDays() {
        return currentCountDays;
    }

    public void setCurrentCountDays(double currentCountDays) {
        this.currentCountDays = currentCountDays;
    }

    public String getEmptyNameStationDeparture() {
        return emptyNameStationDeparture;
    }

    public void setEmptyNameStationDeparture(String emptyNameStationDeparture) {
        this.emptyNameStationDeparture = emptyNameStationDeparture;
    }

    public String getEmptyNameStationDestination() {
        return emptyNameStationDestination;
    }

    public void setEmptyNameStationDestination(String emptyNameStationDestination) {
        this.emptyNameStationDestination = emptyNameStationDestination;
    }

    public String getEmptyDistance() {
        return emptyDistance;
    }

    public void setEmptyDistance(String emptyDistance) {
        this.emptyDistance = emptyDistance;
    }

    public String getEmptyCargo() {
        return emptyCargo;
    }

    public void setEmptyCargo(String emptyCargo) {
        this.emptyCargo = emptyCargo;
    }

    public double getEmptyTariff() {
        return emptyTariff;
    }

    public void setEmptyTariff(double emptyTariff) {
        this.emptyTariff = emptyTariff;
    }

    public double getEmptyCountDays() {
        return emptyCountDays;
    }

    public void setEmptyCountDays(double emptyCountDays) {
        this.emptyCountDays = emptyCountDays;
    }

    public String getSecondNameStationDeparture() {
        return secondNameStationDeparture;
    }

    public void setSecondNameStationDeparture(String secondNameStationDeparture) {
        this.secondNameStationDeparture = secondNameStationDeparture;
    }

    public String getSecondNameStationDestination() {
        return secondNameStationDestination;
    }

    public void setSecondNameStationDestination(String secondNameStationDestination) {
        this.secondNameStationDestination = secondNameStationDestination;
    }

    public String getSecondCargo() {
        return secondCargo;
    }

    public void setSecondCargo(String secondCargo) {
        this.secondCargo = secondCargo;
    }

    public String getSecondDistance() {
        return secondDistance;
    }

    public void setSecondDistance(String secondDistance) {
        this.secondDistance = secondDistance;
    }

    public double getSecondRate() {
        return secondRate;
    }

    public void setSecondRate(double secondRate) {
        this.secondRate = secondRate;
    }

    public double getSecondCountDays() {
        return secondCountDays;
    }

    public void setSecondCountDays(double secondCountDays) {
        this.secondCountDays = secondCountDays;
    }

    public String getEmptySecondNameStationDeparture() {
        return emptySecondNameStationDeparture;
    }

    public void setEmptySecondNameStationDeparture(String emptySecondNameStationDeparture) {
        this.emptySecondNameStationDeparture = emptySecondNameStationDeparture;
    }

    public String getEmptySecondStationDestination() {
        return emptySecondStationDestination;
    }

    public void setEmptySecondStationDestination(String emptySecondStationDestination) {
        this.emptySecondStationDestination = emptySecondStationDestination;
    }

    public String getEmptySecondDistance() {
        return emptySecondDistance;
    }

    public void setEmptySecondDistance(String emptySecondDistance) {
        this.emptySecondDistance = emptySecondDistance;
    }

    public String getEmptySecondCargo() {
        return emptySecondCargo;
    }

    public void setEmptySecondCargo(String emptySecondCargo) {
        this.emptySecondCargo = emptySecondCargo;
    }

    public double getEmptySecondTariff() {
        return emptySecondTariff;
    }

    public void setEmptySecondTariff(double emptySecondTariff) {
        this.emptySecondTariff = emptySecondTariff;
    }

    public double getEmptySecondCountDays() {
        return emptySecondCountDays;
    }

    public double getCurrentCountDaysMinLoad() {
        return currentCountDaysMinLoad;
    }

    public void setCurrentCountDaysMinLoad(double currentCountDaysMinLoad) {
        this.currentCountDaysMinLoad = currentCountDaysMinLoad;
    }

    public double getEmptyCountDaysMinLoad() {
        return emptyCountDaysMinLoad;
    }

    public void setEmptyCountDaysMinLoad(double emptyCountDaysMinLoad) {
        this.emptyCountDaysMinLoad = emptyCountDaysMinLoad;
    }

    public double getSecondCountDaysMinLoad() {
        return secondCountDaysMinLoad;
    }

    public void setSecondCountDaysMinLoad(double secondCountDaysMinLoad) {
        this.secondCountDaysMinLoad = secondCountDaysMinLoad;
    }

    public double getEmptySecondCountDaysMinLoad() {
        return emptySecondCountDaysMinLoad;
    }

    public void setEmptySecondCountDaysMinLoad(double emptySecondCountDaysMinLoad) {
        this.emptySecondCountDaysMinLoad = emptySecondCountDaysMinLoad;
    }

    public int getDistanceSummary() {
        return distanceSummary;
    }

    public void setDistanceSummary(int distanceSummary) {
        this.distanceSummary = distanceSummary;
    }

    public double getCountDaysSummary() {
        return countDaysSummary;
    }

    public void setCountDaysSummary(double countDaysSummary) {
        this.countDaysSummary = countDaysSummary;
    }

    public double getCountDaysSummaryMinLoad() {
        return countDaysSummaryMinLoad;
    }

    public void setCountDaysSummaryMinLoad(double countDaysSummaryMinLoad) {
        this.countDaysSummaryMinLoad = countDaysSummaryMinLoad;
    }

    public double getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(double totalSummary) {
        this.totalSummary = totalSummary;
    }

    public void setEmptySecondCountDays(double emptySecondCountDays) {
        this.emptySecondCountDays = emptySecondCountDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalCalculateRoute that = (TotalCalculateRoute) o;
        return Double.compare(that.currentRate, currentRate) == 0 &&
                Double.compare(that.currentCountDays, currentCountDays) == 0 &&
                Double.compare(that.currentCountDaysMinLoad, currentCountDaysMinLoad) == 0 &&
                Double.compare(that.emptyTariff, emptyTariff) == 0 &&
                Double.compare(that.emptyCountDays, emptyCountDays) == 0 &&
                Double.compare(that.emptyCountDaysMinLoad, emptyCountDaysMinLoad) == 0 &&
                Double.compare(that.secondRate, secondRate) == 0 &&
                Double.compare(that.secondCountDays, secondCountDays) == 0 &&
                Double.compare(that.secondCountDaysMinLoad, secondCountDaysMinLoad) == 0 &&
                Double.compare(that.emptySecondTariff, emptySecondTariff) == 0 &&
                Double.compare(that.emptySecondCountDays, emptySecondCountDays) == 0 &&
                Double.compare(that.emptySecondCountDaysMinLoad, emptySecondCountDaysMinLoad) == 0 &&
                distanceSummary == that.distanceSummary &&
                Double.compare(that.countDaysSummary, countDaysSummary) == 0 &&
                Double.compare(that.countDaysSummaryMinLoad, countDaysSummaryMinLoad) == 0 &&
                Double.compare(that.totalSummary, totalSummary) == 0 &&
                Objects.equals(currentNameStationDeparture, that.currentNameStationDeparture) &&
                Objects.equals(currentNameStationDestination, that.currentNameStationDestination) &&
                Objects.equals(currentCargo, that.currentCargo) &&
                Objects.equals(currentDistance, that.currentDistance) &&
                Objects.equals(emptyNameStationDeparture, that.emptyNameStationDeparture) &&
                Objects.equals(emptyNameStationDestination, that.emptyNameStationDestination) &&
                Objects.equals(emptyDistance, that.emptyDistance) &&
                Objects.equals(emptyCargo, that.emptyCargo) &&
                Objects.equals(secondNameStationDeparture, that.secondNameStationDeparture) &&
                Objects.equals(secondNameStationDestination, that.secondNameStationDestination) &&
                Objects.equals(secondCargo, that.secondCargo) &&
                Objects.equals(secondDistance, that.secondDistance) &&
                Objects.equals(emptySecondNameStationDeparture, that.emptySecondNameStationDeparture) &&
                Objects.equals(emptySecondStationDestination, that.emptySecondStationDestination) &&
                Objects.equals(emptySecondDistance, that.emptySecondDistance) &&
                Objects.equals(emptySecondCargo, that.emptySecondCargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(currentNameStationDeparture, currentNameStationDestination, currentCargo, currentDistance, currentRate, currentCountDays, currentCountDaysMinLoad, emptyNameStationDeparture, emptyNameStationDestination, emptyDistance, emptyCargo, emptyTariff, emptyCountDays, emptyCountDaysMinLoad, secondNameStationDeparture, secondNameStationDestination, secondCargo, secondDistance, secondRate, secondCountDays, secondCountDaysMinLoad, emptySecondNameStationDeparture, emptySecondStationDestination, emptySecondDistance, emptySecondCargo, emptySecondTariff, emptySecondCountDays, emptySecondCountDaysMinLoad, distanceSummary, countDaysSummary, countDaysSummaryMinLoad, totalSummary);
    }

    @Override
    public String toString() {
        return "TotalCalculateRoute{" +
                "currentNameStationDeparture='" + currentNameStationDeparture + '\'' +
                ", currentNameStationDestination='" + currentNameStationDestination + '\'' +
                ", currentCargo='" + currentCargo + '\'' +
                ", currentDistance='" + currentDistance + '\'' +
                ", currentRate=" + currentRate +
                ", currentCountDays=" + currentCountDays +
                ", currentCountDaysMinLoad=" + currentCountDaysMinLoad +
                ", emptyNameStationDeparture='" + emptyNameStationDeparture + '\'' +
                ", emptyNameStationDestination='" + emptyNameStationDestination + '\'' +
                ", emptyDistance='" + emptyDistance + '\'' +
                ", emptyCargo='" + emptyCargo + '\'' +
                ", emptyTariff=" + emptyTariff +
                ", emptyCountDays=" + emptyCountDays +
                ", emptyCountDaysMinLoad=" + emptyCountDaysMinLoad +
                ", secondNameStationDeparture='" + secondNameStationDeparture + '\'' +
                ", secondNameStationDestination='" + secondNameStationDestination + '\'' +
                ", secondCargo='" + secondCargo + '\'' +
                ", secondDistance='" + secondDistance + '\'' +
                ", secondRate=" + secondRate +
                ", secondCountDays=" + secondCountDays +
                ", secondCountDaysMinLoad=" + secondCountDaysMinLoad +
                ", emptySecondNameStationDeparture='" + emptySecondNameStationDeparture + '\'' +
                ", emptySecondStationDestination='" + emptySecondStationDestination + '\'' +
                ", emptySecondDistance='" + emptySecondDistance + '\'' +
                ", emptySecondCargo='" + emptySecondCargo + '\'' +
                ", emptySecondTariff=" + emptySecondTariff +
                ", emptySecondCountDays=" + emptySecondCountDays +
                ", emptySecondCountDaysMinLoad=" + emptySecondCountDaysMinLoad +
                ", distanceSummary=" + distanceSummary +
                ", countDaysSummary=" + countDaysSummary +
                ", countDaysSummaryMinLoad=" + countDaysSummaryMinLoad +
                ", totalSummary=" + totalSummary +
                '}';
    }
}
