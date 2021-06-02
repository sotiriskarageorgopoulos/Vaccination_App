package com.example.nationalvaccinationagency;

import com.example.nationalvaccinationagency.model.Statistics;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface StatisticsService {
    @GET("/api/v1/query/mdg_emvolio")
    Call<List<Statistics>> getStatitisticsResources(@Header("Authorization")String auth, @Query("date_from") String dateFrom, @Query("date_to") String dateTo);
}
