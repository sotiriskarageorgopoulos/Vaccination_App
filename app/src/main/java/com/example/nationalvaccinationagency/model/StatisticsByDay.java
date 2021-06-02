package com.example.nationalvaccinationagency.model;

public class StatisticsByDay {
    private String date;
    private int totalDose1;
    private int totalDose2;
    private int totalVaccinations;
    private int totalVaccinationsUntilLastDay;

    public StatisticsByDay () {}

    public String getDate() {
        return this.date;
    }

    public int getTotalDose1() {
        return this.totalDose1;
    }

    public int getTotalDose2() {
        return this.totalDose2;
    }

    public int getTotalVaccinations() {
        return this.totalVaccinations;
    }

    public int getTotalVaccinationsUntilLastDay() {
        return this.totalVaccinationsUntilLastDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalDose1(int totalDose1) {
        this.totalDose1 = totalDose1;
    }

    public void setTotalDose2(int totalDose2) {
        this.totalDose2 = totalDose2;
    }

    public void setTotalVaccinations(int totalVaccinations) {
        this.totalVaccinations = totalVaccinations;
    }

    public void setTotalVaccinationsUntilLastDay(int totalVaccinationsUntilLastDay) {
        this.totalVaccinationsUntilLastDay = totalVaccinationsUntilLastDay;
    }
}
