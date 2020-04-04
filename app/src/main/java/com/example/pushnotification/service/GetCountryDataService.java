package com.example.pushnotification.service;

import com.example.pushnotification.model.CountryModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCountryDataService {

    @GET("country/get/all")
    Call<CountryModel> getJSON();
}
