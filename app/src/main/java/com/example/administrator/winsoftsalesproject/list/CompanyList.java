package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.PermittedCompanyView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 12/3/16.
 */

public class CompanyList {
    @SerializedName("PermittedCompanyView")
    @Expose
    private ArrayList<PermittedCompanyView> permittedCompanyView = new ArrayList<PermittedCompanyView>();

    /**
     * @return The permittedCompanyView
     */
    public ArrayList<PermittedCompanyView> getPermittedCompanyView() {
        return permittedCompanyView;
    }

    /**
     * @param permittedCompanyView The PermittedCompanyView
     */
    public void setPermittedCompanyView(ArrayList<PermittedCompanyView> permittedCompanyView) {
        this.permittedCompanyView = permittedCompanyView;
    }
}
