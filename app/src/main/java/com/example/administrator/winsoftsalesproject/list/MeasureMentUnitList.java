package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.MeasurementUnitView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 11/30/16.
 */

public class MeasureMentUnitList {

    @SerializedName("MeasurementUnitView")
    @Expose
    private ArrayList<MeasurementUnitView> measurementUnitView = new ArrayList<MeasurementUnitView>();

    /**
     * @return The measurementUnitView
     */
    public ArrayList<MeasurementUnitView> getMeasurementUnitView() {
        return measurementUnitView;
    }

    /**
     * @param measurementUnitView The MeasurementUnitView
     */
    public void setMeasurementUnitView(ArrayList<MeasurementUnitView> measurementUnitView) {
        this.measurementUnitView = measurementUnitView;
    }

}
