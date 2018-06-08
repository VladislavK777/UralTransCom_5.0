package com.uraltranscom.model_ext;

import com.uraltranscom.service.additional.JavaHelperBase;

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

public class TotalCalculateRoute extends JavaHelperBase {

    // Параметры текущего положения вагона
    private String currentNameStationDeparture;
    private String currentRoadStationDeparture;
    private String currentNameStationDestination;
    private String currentRoadStationDestination;
    private String currentCargo;
    private String currentDistance;
    private double currentRate;
    private double currentCountDays;
    private double currentCountDaysWithLoad;

    // Параметры порожнего рейса
    private String emptyNameStationDeparture;
    private String emptyRoadStationDeparture;
    private String emptyNameStationDestination;
    private String emptyRoadStationDestination;
    private String emptyDistance;
    private String emptyCargo;
    private double emptyTariff;
    private double emptyCountDays;
    private double emptyCountDaysWithLoad;

    // Парметры второго рейса
    private String secondNameStationDeparture;
    private String secondRoadStationDeparture;
    private String secondNameStationDestination;
    private String secondRoadStationDestination;
    private String secondCargo;
    private String secondDistance;
    private double secondRate;
    private double secondCountDays;
    private double secondCountDaysWithLoad;

    // Параметры второго порожнего рейса
    private String emptySecondNameStationDeparture;
    private String emptySecondRoadStationDeparture;
    private String emptySecondNameStationDestination;
    private String emptySecondRoadStationDestination;
    private String emptySecondDistance;
    private String emptySecondCargo;
    private double emptySecondTariff;
    private double emptySecondCountDays;
    private double emptySecondCountDaysWithLoad;

    // Итоговые суммы
    private int distanceSummary;
    private double countDaysSummary;
    private double countDaysSummaryWithLoad;
    private double totalSummary;

    // Параметры выгрузки/погрузки
    private double firstLoadingWagon;
    private double secondLoadingWagon;
    private double unloadingWagon;
    private double summaryLoading;

    public TotalCalculateRoute(String currentNameStationDeparture, String currentRoadStationDeparture, String currentNameStationDestination, String currentRoadStationDestination, String currentCargo, String currentDistance, double currentRate, double currentCountDays, String emptyNameStationDeparture, String emptyRoadStationDeparture, String emptyNameStationDestination, String emptyRoadStationDestination, String emptyDistance, double emptyTariff, double emptyCountDays, String secondNameStationDeparture, String secondRoadStationDeparture, String secondNameStationDestination, String secondRoadStationDestination, String secondCargo, String secondDistance, double secondRate, double secondCountDays, String emptySecondNameStationDeparture, String emptySecondRoadStationDeparture, String emptySecondNameStationDestination, String emptySecondRoadStationDestination, String emptySecondDistance, double emptySecondTariff, double emptySecondCountDays) {
        this.currentNameStationDeparture = currentNameStationDeparture;
        this.currentRoadStationDeparture = currentRoadStationDeparture;
        this.currentNameStationDestination = currentNameStationDestination;
        this.currentRoadStationDestination = currentRoadStationDestination;
        this.currentCargo = currentCargo;
        this.currentDistance = currentDistance;
        this.currentRate = Math.round(currentRate * 100) / 100.00d;
        this.currentCountDays = currentCountDays;
        this.emptyNameStationDeparture = emptyNameStationDeparture;
        this.emptyRoadStationDeparture = emptyRoadStationDeparture;
        this.emptyNameStationDestination = emptyNameStationDestination;
        this.emptyRoadStationDestination = emptyRoadStationDestination;
        this.emptyDistance = emptyDistance;
        this.emptyTariff = Math.round(emptyTariff * 100) / 100.00d;
        this.emptyCountDays = emptyCountDays;
        this.secondNameStationDeparture = secondNameStationDeparture;
        this.secondRoadStationDeparture = secondRoadStationDeparture;
        this.secondNameStationDestination = secondNameStationDestination;
        this.secondRoadStationDestination = secondRoadStationDestination;
        this.secondCargo = secondCargo;
        this.secondDistance = secondDistance;
        this.secondRate = Math.round(secondRate * 100) / 100.00d;
        this.secondCountDays = secondCountDays;
        this.emptySecondNameStationDeparture = emptySecondNameStationDeparture;
        this.emptySecondRoadStationDeparture = emptySecondRoadStationDeparture;
        this.emptySecondNameStationDestination = emptySecondNameStationDestination;
        this.emptySecondRoadStationDestination = emptySecondRoadStationDestination;
        this.emptySecondDistance = emptySecondDistance;
        this.emptySecondTariff = Math.round(emptySecondTariff * 100) / 100.00d;
        this.emptySecondCountDays = emptySecondCountDays;
        this.totalSummary = Math.round((currentRate - emptyTariff + secondRate - emptySecondTariff) * 100) / 100.00d;
        this.currentCountDaysWithLoad = currentCountDays + FIRST_LOADING_WAGON_KR;
        this.emptyCountDaysWithLoad = emptyCountDays + UNLOADING_WAGON;
        this.secondCountDaysWithLoad = secondCountDays + SECOND_LOADING_WAGON_KR;
        this.emptySecondCountDaysWithLoad = emptySecondCountDays + UNLOADING_WAGON;
        this.distanceSummary = Integer.parseInt(currentDistance) + Integer.parseInt(emptyDistance) + Integer.parseInt(secondDistance) + Integer.parseInt(emptySecondDistance);
        this.countDaysSummary = currentCountDays + emptyCountDays + secondCountDays + emptySecondCountDays;
        this.countDaysSummaryWithLoad = this.currentCountDaysWithLoad + this.emptyCountDaysWithLoad + this.secondCountDaysWithLoad + this.emptySecondCountDaysWithLoad;
        this.firstLoadingWagon = FIRST_LOADING_WAGON_KR;
        this.secondLoadingWagon = SECOND_LOADING_WAGON_KR;
        this.unloadingWagon = UNLOADING_WAGON;
        this.summaryLoading = this.firstLoadingWagon + this.unloadingWagon + this.secondLoadingWagon + this.unloadingWagon;
        this.emptyCargo = "порожняк";
        this.emptySecondCargo = "порожняк";
    }

    public String getCurrentNameStationDeparture() {
        return currentNameStationDeparture;
    }

    public void setCurrentNameStationDeparture(String currentNameStationDeparture) {
        this.currentNameStationDeparture = currentNameStationDeparture;
    }

    public String getCurrentRoadStationDeparture() {
        return currentRoadStationDeparture;
    }

    public void setCurrentRoadStationDeparture(String currentRoadStationDeparture) {
        this.currentRoadStationDeparture = currentRoadStationDeparture;
    }

    public String getCurrentNameStationDestination() {
        return currentNameStationDestination;
    }

    public void setCurrentNameStationDestination(String currentNameStationDestination) {
        this.currentNameStationDestination = currentNameStationDestination;
    }

    public String getCurrentRoadStationDestination() {
        return currentRoadStationDestination;
    }

    public void setCurrentRoadStationDestination(String currentRoadStationDestination) {
        this.currentRoadStationDestination = currentRoadStationDestination;
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

    public double getCurrentCountDaysWithLoad() {
        return currentCountDaysWithLoad;
    }

    public void setCurrentCountDaysWithLoad(double currentCountDaysWithLoad) {
        this.currentCountDaysWithLoad = currentCountDaysWithLoad;
    }

    public String getEmptyNameStationDeparture() {
        return emptyNameStationDeparture;
    }

    public void setEmptyNameStationDeparture(String emptyNameStationDeparture) {
        this.emptyNameStationDeparture = emptyNameStationDeparture;
    }

    public String getEmptyRoadStationDeparture() {
        return emptyRoadStationDeparture;
    }

    public void setEmptyRoadStationDeparture(String emptyRoadStationDeparture) {
        this.emptyRoadStationDeparture = emptyRoadStationDeparture;
    }

    public String getEmptyNameStationDestination() {
        return emptyNameStationDestination;
    }

    public void setEmptyNameStationDestination(String emptyNameStationDestination) {
        this.emptyNameStationDestination = emptyNameStationDestination;
    }

    public String getEmptyRoadStationDestination() {
        return emptyRoadStationDestination;
    }

    public void setEmptyRoadStationDestination(String emptyRoadStationDestination) {
        this.emptyRoadStationDestination = emptyRoadStationDestination;
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

    public double getEmptyCountDaysWithLoad() {
        return emptyCountDaysWithLoad;
    }

    public void setEmptyCountDaysWithLoad(double emptyCountDaysWithLoad) {
        this.emptyCountDaysWithLoad = emptyCountDaysWithLoad;
    }

    public String getSecondNameStationDeparture() {
        return secondNameStationDeparture;
    }

    public void setSecondNameStationDeparture(String secondNameStationDeparture) {
        this.secondNameStationDeparture = secondNameStationDeparture;
    }

    public String getSecondRoadStationDeparture() {
        return secondRoadStationDeparture;
    }

    public void setSecondRoadStationDeparture(String secondRoadStationDeparture) {
        this.secondRoadStationDeparture = secondRoadStationDeparture;
    }

    public String getSecondNameStationDestination() {
        return secondNameStationDestination;
    }

    public void setSecondNameStationDestination(String secondNameStationDestination) {
        this.secondNameStationDestination = secondNameStationDestination;
    }

    public String getSecondRoadStationDestination() {
        return secondRoadStationDestination;
    }

    public void setSecondRoadStationDestination(String secondRoadStationDestination) {
        this.secondRoadStationDestination = secondRoadStationDestination;
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

    public double getSecondCountDaysWithLoad() {
        return secondCountDaysWithLoad;
    }

    public void setSecondCountDaysWithLoad(double secondCountDaysWithLoad) {
        this.secondCountDaysWithLoad = secondCountDaysWithLoad;
    }

    public String getEmptySecondNameStationDeparture() {
        return emptySecondNameStationDeparture;
    }

    public void setEmptySecondNameStationDeparture(String emptySecondNameStationDeparture) {
        this.emptySecondNameStationDeparture = emptySecondNameStationDeparture;
    }

    public String getEmptySecondRoadStationDeparture() {
        return emptySecondRoadStationDeparture;
    }

    public void setEmptySecondRoadStationDeparture(String emptySecondRoadStationDeparture) {
        this.emptySecondRoadStationDeparture = emptySecondRoadStationDeparture;
    }

    public String getEmptySecondNameStationDestination() {
        return emptySecondNameStationDestination;
    }

    public void setEmptySecondNameStationDestination(String emptySecondNameStationDestination) {
        this.emptySecondNameStationDestination = emptySecondNameStationDestination;
    }

    public String getEmptySecondRoadStationDestination() {
        return emptySecondRoadStationDestination;
    }

    public void setEmptySecondRoadStationDestination(String emptySecondRoadStationDestination) {
        this.emptySecondRoadStationDestination = emptySecondRoadStationDestination;
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

    public void setEmptySecondCountDays(double emptySecondCountDays) {
        this.emptySecondCountDays = emptySecondCountDays;
    }

    public double getEmptySecondCountDaysWithLoad() {
        return emptySecondCountDaysWithLoad;
    }

    public void setEmptySecondCountDaysWithLoad(double emptySecondCountDaysWithLoad) {
        this.emptySecondCountDaysWithLoad = emptySecondCountDaysWithLoad;
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

    public double getCountDaysSummaryWithLoad() {
        return countDaysSummaryWithLoad;
    }

    public void setCountDaysSummaryWithLoad(double countDaysSummaryWithLoad) {
        this.countDaysSummaryWithLoad = countDaysSummaryWithLoad;
    }

    public double getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(double totalSummary) {
        this.totalSummary = totalSummary;
    }

    public double getFirstLoadingWagon() {
        return firstLoadingWagon;
    }

    public void setFirstLoadingWagon(double firstLoadingWagon) {
        this.firstLoadingWagon = firstLoadingWagon;
    }

    public double getSecondLoadingWagon() {
        return secondLoadingWagon;
    }

    public void setSecondLoadingWagon(double secondLoadingWagon) {
        this.secondLoadingWagon = secondLoadingWagon;
    }

    public double getUnloadingWagon() {
        return unloadingWagon;
    }

    public void setUnloadingWagon(double unloadingWagon) {
        this.unloadingWagon = unloadingWagon;
    }

    public double getSummaryLoading() {
        return summaryLoading;
    }

    public void setSummaryLoading(double summaryLoading) {
        this.summaryLoading = summaryLoading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalCalculateRoute that = (TotalCalculateRoute) o;
        return Double.compare(that.currentRate, currentRate) == 0 &&
                Double.compare(that.currentCountDays, currentCountDays) == 0 &&
                Double.compare(that.currentCountDaysWithLoad, currentCountDaysWithLoad) == 0 &&
                Double.compare(that.emptyTariff, emptyTariff) == 0 &&
                Double.compare(that.emptyCountDays, emptyCountDays) == 0 &&
                Double.compare(that.emptyCountDaysWithLoad, emptyCountDaysWithLoad) == 0 &&
                Double.compare(that.secondRate, secondRate) == 0 &&
                Double.compare(that.secondCountDays, secondCountDays) == 0 &&
                Double.compare(that.secondCountDaysWithLoad, secondCountDaysWithLoad) == 0 &&
                Double.compare(that.emptySecondTariff, emptySecondTariff) == 0 &&
                Double.compare(that.emptySecondCountDays, emptySecondCountDays) == 0 &&
                Double.compare(that.emptySecondCountDaysWithLoad, emptySecondCountDaysWithLoad) == 0 &&
                distanceSummary == that.distanceSummary &&
                Double.compare(that.countDaysSummary, countDaysSummary) == 0 &&
                Double.compare(that.countDaysSummaryWithLoad, countDaysSummaryWithLoad) == 0 &&
                Double.compare(that.totalSummary, totalSummary) == 0 &&
                Double.compare(that.firstLoadingWagon, firstLoadingWagon) == 0 &&
                Double.compare(that.secondLoadingWagon, secondLoadingWagon) == 0 &&
                Double.compare(that.unloadingWagon, unloadingWagon) == 0 &&
                Double.compare(that.summaryLoading, summaryLoading) == 0 &&
                Objects.equals(currentNameStationDeparture, that.currentNameStationDeparture) &&
                Objects.equals(currentRoadStationDeparture, that.currentRoadStationDeparture) &&
                Objects.equals(currentNameStationDestination, that.currentNameStationDestination) &&
                Objects.equals(currentRoadStationDestination, that.currentRoadStationDestination) &&
                Objects.equals(currentCargo, that.currentCargo) &&
                Objects.equals(currentDistance, that.currentDistance) &&
                Objects.equals(emptyNameStationDeparture, that.emptyNameStationDeparture) &&
                Objects.equals(emptyRoadStationDeparture, that.emptyRoadStationDeparture) &&
                Objects.equals(emptyNameStationDestination, that.emptyNameStationDestination) &&
                Objects.equals(emptyRoadStationDestination, that.emptyRoadStationDestination) &&
                Objects.equals(emptyDistance, that.emptyDistance) &&
                Objects.equals(emptyCargo, that.emptyCargo) &&
                Objects.equals(secondNameStationDeparture, that.secondNameStationDeparture) &&
                Objects.equals(secondRoadStationDeparture, that.secondRoadStationDeparture) &&
                Objects.equals(secondNameStationDestination, that.secondNameStationDestination) &&
                Objects.equals(secondRoadStationDestination, that.secondRoadStationDestination) &&
                Objects.equals(secondCargo, that.secondCargo) &&
                Objects.equals(secondDistance, that.secondDistance) &&
                Objects.equals(emptySecondNameStationDeparture, that.emptySecondNameStationDeparture) &&
                Objects.equals(emptySecondRoadStationDeparture, that.emptySecondRoadStationDeparture) &&
                Objects.equals(emptySecondNameStationDestination, that.emptySecondNameStationDestination) &&
                Objects.equals(emptySecondRoadStationDestination, that.emptySecondRoadStationDestination) &&
                Objects.equals(emptySecondDistance, that.emptySecondDistance) &&
                Objects.equals(emptySecondCargo, that.emptySecondCargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(currentNameStationDeparture, currentRoadStationDeparture, currentNameStationDestination, currentRoadStationDestination, currentCargo, currentDistance, currentRate, currentCountDays, currentCountDaysWithLoad, emptyNameStationDeparture, emptyRoadStationDeparture, emptyNameStationDestination, emptyRoadStationDestination, emptyDistance, emptyCargo, emptyTariff, emptyCountDays, emptyCountDaysWithLoad, secondNameStationDeparture, secondRoadStationDeparture, secondNameStationDestination, secondRoadStationDestination, secondCargo, secondDistance, secondRate, secondCountDays, secondCountDaysWithLoad, emptySecondNameStationDeparture, emptySecondRoadStationDeparture, emptySecondNameStationDestination, emptySecondRoadStationDestination, emptySecondDistance, emptySecondCargo, emptySecondTariff, emptySecondCountDays, emptySecondCountDaysWithLoad, distanceSummary, countDaysSummary, countDaysSummaryWithLoad, totalSummary, firstLoadingWagon, secondLoadingWagon, unloadingWagon, summaryLoading);
    }

    @Override
    public String toString() {
        return "TotalCalculateRoute{" +
                "currentNameStationDeparture='" + currentNameStationDeparture + '\'' +
                ", currentRoadStationDeparture='" + currentRoadStationDeparture + '\'' +
                ", currentNameStationDestination='" + currentNameStationDestination + '\'' +
                ", currentRoadStationDestination='" + currentRoadStationDestination + '\'' +
                ", currentCargo='" + currentCargo + '\'' +
                ", currentDistance='" + currentDistance + '\'' +
                ", currentRate=" + currentRate +
                ", currentCountDays=" + currentCountDays +
                ", currentCountDaysWithLoad=" + currentCountDaysWithLoad +
                ", emptyNameStationDeparture='" + emptyNameStationDeparture + '\'' +
                ", emptyRoadStationDeparture='" + emptyRoadStationDeparture + '\'' +
                ", emptyNameStationDestination='" + emptyNameStationDestination + '\'' +
                ", emptyRoadStationDestination='" + emptyRoadStationDestination + '\'' +
                ", emptyDistance='" + emptyDistance + '\'' +
                ", emptyCargo='" + emptyCargo + '\'' +
                ", emptyTariff=" + emptyTariff +
                ", emptyCountDays=" + emptyCountDays +
                ", emptyCountDaysWithLoad=" + emptyCountDaysWithLoad +
                ", secondNameStationDeparture='" + secondNameStationDeparture + '\'' +
                ", secondRoadStationDeparture='" + secondRoadStationDeparture + '\'' +
                ", secondNameStationDestination='" + secondNameStationDestination + '\'' +
                ", secondRoadStationDestination='" + secondRoadStationDestination + '\'' +
                ", secondCargo='" + secondCargo + '\'' +
                ", secondDistance='" + secondDistance + '\'' +
                ", secondRate=" + secondRate +
                ", secondCountDays=" + secondCountDays +
                ", secondCountDaysWithLoad=" + secondCountDaysWithLoad +
                ", emptySecondNameStationDeparture='" + emptySecondNameStationDeparture + '\'' +
                ", emptySecondRoadStationDeparture='" + emptySecondRoadStationDeparture + '\'' +
                ", emptySecondNameStationDestination='" + emptySecondNameStationDestination + '\'' +
                ", emptySecondRoadStationDestination='" + emptySecondRoadStationDestination + '\'' +
                ", emptySecondDistance='" + emptySecondDistance + '\'' +
                ", emptySecondCargo='" + emptySecondCargo + '\'' +
                ", emptySecondTariff=" + emptySecondTariff +
                ", emptySecondCountDays=" + emptySecondCountDays +
                ", emptySecondCountDaysWithLoad=" + emptySecondCountDaysWithLoad +
                ", distanceSummary=" + distanceSummary +
                ", countDaysSummary=" + countDaysSummary +
                ", countDaysSummaryWithLoad=" + countDaysSummaryWithLoad +
                ", totalSummary=" + totalSummary +
                ", firstLoadingWagon=" + firstLoadingWagon +
                ", secondLoadingWagon=" + secondLoadingWagon +
                ", unloadingWagon=" + unloadingWagon +
                ", summaryLoading=" + summaryLoading +
                '}';
    }
}
