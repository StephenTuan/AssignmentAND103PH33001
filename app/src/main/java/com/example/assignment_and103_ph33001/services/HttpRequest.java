package com.example.assignment_and103_ph33001.services;

import static com.example.assignment_and103_ph33001.services.ApiService.BASE_URl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private static ApiService service;

    public HttpRequest(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        
        service = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService.class);
    }
    public static ApiService callApi(){
        return  service;
    }
}
