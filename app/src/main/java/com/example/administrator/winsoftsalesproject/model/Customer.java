package com.example.administrator.winsoftsalesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt on 11/5/16.
 */

public class Customer {
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address")
    @Expose
    private String address;

    /**
     *
     * @return
     * The customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     * The CustomerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     * The customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     *
     * @param customerCode
     * The CustomerCode
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     *
     * @return
     * The customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName
     * The CustomerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The PhoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
