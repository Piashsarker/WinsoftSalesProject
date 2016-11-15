package com.example.administrator.winsoftsalesproject.model;

import java.io.Serializable;

/**
 * Created by pt on 11/9/16.
 */

public class Item  implements Serializable{
    private String date;
    private String reference;
    private String customerCode;
    private String itemCode;
    private String minimumUnit;
    private String balance;
    private String quantity;

    public Item(String date, String reference, String customerCode, String itemCode, String minimumUnit, String balance, String quantity) {
        this.date = date;
        this.reference = reference;
        this.customerCode = customerCode;
        this.itemCode = itemCode;
        this.minimumUnit = minimumUnit;
        this.balance = balance;
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMinimumUnit() {
        return minimumUnit;
    }

    public void setMinimumUnit(String minimumUnit) {
        this.minimumUnit = minimumUnit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


}
