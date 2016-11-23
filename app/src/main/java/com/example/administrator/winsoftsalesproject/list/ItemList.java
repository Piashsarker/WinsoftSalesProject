package com.example.administrator.winsoftsalesproject.list;

import com.example.administrator.winsoftsalesproject.model.ItemView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by pt on 11/19/16.
 */

public class ItemList {
    @SerializedName("ItemView")
    @Expose
    private ArrayList<ItemView> itemView = new ArrayList<ItemView>();

    /**
     *
     * @return
     * The itemView
     */
    public ArrayList<ItemView> getItemView() {
        return itemView;
    }

    /**
     *
     * @param itemView
     * The ItemView
     */
    public void setItemView(ArrayList<ItemView> itemView) {
        this.itemView = itemView;
    }

}
