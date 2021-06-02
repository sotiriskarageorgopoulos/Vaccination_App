package com.example.nationalvaccinationagency.model;

import com.google.gson.annotations.SerializedName;

public class Statistics {
    @SerializedName("area")
    public String area;
    @SerializedName("areaid")
    public int areaId;
    @SerializedName("dailydose1")
    public int dailyDose1;
    @SerializedName("dailydose2")
    public int dailyDose2;
    @SerializedName("daydiff")
    public int dayDiff;
    @SerializedName("daytotal")
    public int dayTotal;
    @SerializedName("referencedate")
    public String referenceDate;
    @SerializedName("totaldistinctpersons")
    public int totalDistinctPersons;
    @SerializedName("totaldose1")
    public int totalDose1;
    @SerializedName("totaldose2")
    public int totalDose2;
    @SerializedName("totalvaccinations")
    public int totalVaccinations;
}
