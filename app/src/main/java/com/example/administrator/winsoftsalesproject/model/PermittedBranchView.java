package com.example.administrator.winsoftsalesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt on 12/3/16.
 */

public class PermittedBranchView {

    @SerializedName("BranchId")
    @Expose
    private String branchId;
    @SerializedName("BranchName")
    @Expose
    private String branchName;

    /**
     * @return The branchId
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * @param branchId The BranchId
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * @return The branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName The BranchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
