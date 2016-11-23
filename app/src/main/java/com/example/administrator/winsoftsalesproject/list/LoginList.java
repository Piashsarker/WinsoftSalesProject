package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.LoginTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 11/13/16.
 */

public class LoginList {
    @SerializedName("LoginTable")
    @Expose
    private ArrayList<LoginTable> loginTable = new ArrayList<>();

    /**
     *
     * @return
     * The loginTable
     */
    public ArrayList<LoginTable> getLoginTable() {
        return loginTable;
    }

    /**
     *
     * @param loginTable
     * The LoginTable
     */
    public void setLoginTable(ArrayList<LoginTable> loginTable) {
        this.loginTable = loginTable;
    }
}
