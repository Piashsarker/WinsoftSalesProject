package com.example.administrator.winsoftsalesproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.SalesView;

import java.util.ArrayList;

/**
 * Created by pt on 11/5/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<SalesView> productArrayList;
    private Context context ;


    public ProductAdapter(Context context, ArrayList<SalesView> productArrayList) {
        this.context = context ;
        this.productArrayList = productArrayList ;


    }

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sales_item_approval, parent, false);
        return new ProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {

        holder.salesId.setText("ID: " + productArrayList.get(position).getSalesId());
        holder.salesDate.setText("Date: " + productArrayList.get(position).getSalesDate().substring(0, 10));
        holder.accountNumber.setText("Account No: " + productArrayList.get(position).getAccountNo());
        holder.customerName.setText("Name: " + productArrayList.get(position).getCustomerName());
        holder.totalPrice.setText("Price: " + productArrayList.get(position).getTotalPrice().toString());
        holder.totalVat.setText("Vat: " + productArrayList.get(position).getTotalVat().toString());
        holder.totalDiscount.setText("Discount: " + productArrayList.get(position).getTotalDiscount().toString());
        holder.goodsDeliveryFlags.setText("Status: " + productArrayList.get(position).getGoodsDeliveryFlag());

        holder.approve.setText("Approve");
        holder.reject.setText("Reject");

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView approve, reject, salesId, salesDate, accountNumber, customerName, totalPrice, totalVat, totalDiscount, grandTotal, goodsDeliveryFlags;

        public MyViewHolder(View itemView) {
            super(itemView);

            salesId = (TextView) itemView.findViewById(R.id.sales_id);
            salesDate = (TextView) itemView.findViewById(R.id.sales_date);
            accountNumber = (TextView) itemView.findViewById(R.id.account_no);
            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            totalPrice = (TextView) itemView.findViewById(R.id.total_price);
            totalVat = (TextView) itemView.findViewById(R.id.total_vat);
            totalDiscount = (TextView) itemView.findViewById(R.id.total_discount);
            grandTotal = (TextView) itemView.findViewById(R.id.grand_total);

            goodsDeliveryFlags = (TextView) itemView.findViewById(R.id.status);

            approve = (TextView) itemView.findViewById(R.id.approve);
            reject = (TextView) itemView.findViewById(R.id.reject);






        }
    }
}
