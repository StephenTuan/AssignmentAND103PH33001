package com.example.assignment_and103_ph33001.services;

import com.example.assignment_and103_ph33001.Model.FruitItem;
import com.example.assignment_and103_ph33001.Model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    public static String BASE_URl = "http://10.0.2.2:3000/";
    @GET("api/list")
    Call<Response<ArrayList<FruitItem>>> getListFruist();

    @POST("api/add_fruist")
    Call<Response<FruitItem>> addFruist(@Body FruitItem fruist); // body dùng để gửi dữ liệu dưới dạng body của http request

    @PUT("api/update_fruist/{id}")
    Call<Response<FruitItem>> updateFruist(@Path("id") String id, @Body FruitItem fruist);

    @DELETE("api/delete_fruist/{id}")
    Call<Response<Void>> deleteFruist(@Path("id") String id);

}
