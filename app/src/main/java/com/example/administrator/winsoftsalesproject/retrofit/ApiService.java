package com.example.administrator.winsoftsalesproject.retrofit;

import com.example.administrator.winsoftsalesproject.list.BranchList;
import com.example.administrator.winsoftsalesproject.list.CompanyList;
import com.example.administrator.winsoftsalesproject.list.CustomerList;
import com.example.administrator.winsoftsalesproject.list.ItemList;
import com.example.administrator.winsoftsalesproject.list.LoginList;
import com.example.administrator.winsoftsalesproject.list.MeasureMentUnitList;
import com.example.administrator.winsoftsalesproject.list.SalesList;
import com.example.administrator.winsoftsalesproject.model.PostingResponse;
import com.example.administrator.winsoftsalesproject.model.Product;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("ItemInfo.aspx")
    Call<MeasureMentUnitList> getMeasurementList(@Query("key") String key, @Query("actionType") String actionType, @Query("ItemCode") String itemId);

    @GET("ComboBox.aspx")
    Call<BranchList> getBranchlist(@Query("key") String key, @Query("actionType") String actionType, @Query("UserGroupId") String userGroupId, @Query("EmployeeId") String employeeId, @Query("BranchId") String branchId, @Query("CompanyId") String companyId);

    @GET("ComboBox.aspx")
    Call<CompanyList> getCompanyList(@Query("key") String key, @Query("actionType") String actionType, @Query("UserGroupId") String userGroupId, @Query("EmployeeId") String employeeId, @Query("BranchId") String branchId);

    @POST("SalesItem.aspx")
    Call<ResponseBody> postSalesItem(@Query("customerCode") String customerCode, @Body Product product);

    @POST("SalesInfo.aspx")
    Call <PostingResponse> postSelesItem(@Query("key")String key , @Query("actionType")String actionType, @Query("SalesId")String salesId, @Query("SalesDate") String salesDate, @Query("ReferenceNo")String referenceNO, @Query("BranchId")String branchId, @Query("CustomerCode")String customerCode, @Query("Itemcode")String itemCode, @Query("Munit")String mUnit, @Query("SaleQty")String salesQty, @Query("UpdatedBy")String updatedBy);

}
