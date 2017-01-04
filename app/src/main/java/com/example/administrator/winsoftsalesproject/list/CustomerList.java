package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.Customer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 11/19/16.
 */

public class CustomerList {

    @SerializedName("CustomerView")
    @Expose
    private ArrayList<Customer> customerView = new ArrayList<Customer>();

    /**
     *
     * @return
     * The customerView
     */
    public ArrayList<Customer> getCustomerView() {
        return customerView;
    }

    /**
     *
     * @param customerView
     * The CustomerView
     */
    public void setCustomerView(ArrayList<Customer> customerView) {
        this.customerView = customerView;
    }
}
