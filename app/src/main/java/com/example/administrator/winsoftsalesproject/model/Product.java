package com.example.administrator.winsoftsalesproject.model;

/**
 * Created by pt on 11/5/16.
 */

public class Product {
    private String productName;
    private String invoiceNo;
    private String salesDate;
    private String amount;

    public Product(String productName, String invoiceNo, String salesDate, String amount) {
        this.productName = productName;
        this.invoiceNo = invoiceNo;
        this.salesDate = salesDate;
        this.amount = amount;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
