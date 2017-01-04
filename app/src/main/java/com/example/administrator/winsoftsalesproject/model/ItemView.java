package com.example.administrator.winsoftsalesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt on 11/19/16.
 */

public class ItemView {
    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemGroupId")
    @Expose
    private Integer itemGroupId;
    @SerializedName("GroupName")
    @Expose
    private String groupName;
    @SerializedName("ItemTypeId")
    @Expose
    private Integer itemTypeId;
    @SerializedName("ItemTypeName")
    @Expose
    private String itemTypeName;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("ItemModelId")
    @Expose
    private Integer itemModelId;
    @SerializedName("ItemModelName")
    @Expose
    private String itemModelName;
    @SerializedName("CostPrice")
    @Expose
    private Double costPrice;
    @SerializedName("SalesPrice")
    @Expose
    private Double salesPrice;

    /**
     *
     * @return
     * The itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     *
     * @param itemId
     * The ItemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     *
     * @return
     * The itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     *
     * @param itemCode
     * The ItemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     *
     * @return
     * The itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @param itemName
     * The ItemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     *
     * @return
     * The itemGroupId
     */
    public Integer getItemGroupId() {
        return itemGroupId;
    }

    /**
     *
     * @param itemGroupId
     * The ItemGroupId
     */
    public void setItemGroupId(Integer itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    /**
     *
     * @return
     * The groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     * The GroupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * @return
     * The itemTypeId
     */
    public Integer getItemTypeId() {
        return itemTypeId;
    }

    /**
     *
     * @param itemTypeId
     * The ItemTypeId
     */
    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    /**
     *
     * @return
     * The itemTypeName
     */
    public String getItemTypeName() {
        return itemTypeName;
    }

    /**
     *
     * @param itemTypeName
     * The ItemTypeName
     */
    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    /**
     *
     * @return
     * The brandId
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     *
     * @param brandId
     * The BrandId
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     *
     * @return
     * The brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     *
     * @param brandName
     * The BrandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     *
     * @return
     * The itemModelId
     */
    public Integer getItemModelId() {
        return itemModelId;
    }

    /**
     *
     * @param itemModelId
     * The ItemModelId
     */
    public void setItemModelId(Integer itemModelId) {
        this.itemModelId = itemModelId;
    }

    /**
     *
     * @return
     * The itemModelName
     */
    public String getItemModelName() {
        return itemModelName;
    }

    /**
     *
     * @param itemModelName
     * The ItemModelName
     */
    public void setItemModelName(String itemModelName) {
        this.itemModelName = itemModelName;
    }

    /**
     *
     * @return
     * The costPrice
     */
    public Double getCostPrice() {
        return costPrice;
    }

    /**
     *
     * @param costPrice
     * The CostPrice
     */
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    /**
     *
     * @return
     * The salesPrice
     */
    public Double getSalesPrice() {
        return salesPrice;
    }

    /**
     *
     * @param salesPrice
     * The SalesPrice
     */
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

}
