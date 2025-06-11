package com.example.assignment_and103_ph33001.services;

import static com.example.assignment_and103_ph33001.services.ApiService.BASE_URl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiService service;

    public HttpRequest(){
        service = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);
    }
    public ApiService callApi(){
        return  service;
    }
}
