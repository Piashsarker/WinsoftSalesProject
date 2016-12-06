package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.PermittedBranchView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 12/3/16.
 */

public class BranchList {
    @SerializedName("PermittedBranchView")
    @Expose
    private ArrayList<PermittedBranchView> permittedBranchView = new ArrayList<PermittedBranchView>();

    /**
     * @return The permittedBranchView
     */
    public ArrayList<PermittedBranchView> getPermittedBranchView() {
        return permittedBranchView;
    }

    /**
     * @param permittedBranchView The PermittedBranchView
     */
    public void setPermittedBranchView(ArrayList<PermittedBranchView> permittedBranchView) {
        this.permittedBranchView = permittedBranchView;
    }

}
