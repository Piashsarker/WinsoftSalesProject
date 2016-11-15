package com.example.administrator.winsoftsalesproject.model;

/**
 * Created by pt on 11/5/16.
 */

public class Customer {

    private String name ;
    private String designation ;
    private int photo ;

    public Customer(String name, String designation,String address,int photo) {
        this.name = name;
        this.designation = designation;
        this.photo = photo;
        this.address = address;
    }

    private String address;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
