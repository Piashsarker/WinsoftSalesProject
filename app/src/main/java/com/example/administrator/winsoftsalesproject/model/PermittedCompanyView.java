package com.example.administrator.winsoftsalesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt on 12/3/16.
 */

public class PermittedCompanyView {

    @SerializedName("CompanyId")
    @Expose
    private Integer companyId;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;

    /**
     * @return The companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId The CompanyId
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * @return The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The CompanyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
