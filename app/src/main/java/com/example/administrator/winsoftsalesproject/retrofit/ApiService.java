package com.example.administrator.winsoftsalesproject.retrofit;

import com.example.administrator.winsoftsalesproject.list.LoginList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pt on 11/13/16.
 */

public interface ApiService {

    @GET("login.aspx")
    Call<LoginList> getLoginDetails(
            @Query("key") String key,
            @Query("user") String user,
            @Query("pass") String pass);
}
