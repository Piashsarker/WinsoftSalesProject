package com.example.administrator.winsoftsalesproject.retrofit;

import com.example.administrator.winsoftsalesproject.list.CustomerList;
import com.example.administrator.winsoftsalesproject.list.ItemList;
import com.example.administrator.winsoftsalesproject.list.LoginList;
import com.example.administrator.winsoftsalesproject.list.SalesList;

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

    @GET("ItemInfo.aspx")
    Call<ItemList> getItemDetails(@Query("key") String key,
                                  @Query("actionType") String actionType);

    @GET("CustomerInfo.aspx")
    Call<CustomerList> getCustomerDetails(@Query("key") String key,
                                          @Query("actionType") String actionType);

    @GET("SalesInfo.aspx")
    Call<SalesList> getSalesItem(
            @Query("key") String key, @Query("actionType") String actionType, @Query("UserId") String userId
    );
}
