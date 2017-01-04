package com.example.administrator.winsoftsalesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt on 11/30/16.
 */

public class MeasurementUnitView {
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("MeasurementUnit")
    @Expose
    private String measurementUnit;

    /**
     * @return The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity The Quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return The measurementUnit
     */
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * @param measurementUnit The MeasurementUnitView
     */
    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
