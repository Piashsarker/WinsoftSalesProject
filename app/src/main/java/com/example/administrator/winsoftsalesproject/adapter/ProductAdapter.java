package com.example.administrator.winsoftsalesproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.Product;

import java.util.ArrayList;

/**
 * Created by pt on 11/5/16.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<Product> productArrayList;
    private Context context ;


    public ProductAdapter(Context context , ArrayList<Product> productArrayList){
        this.context = context ;
        this.productArrayList = productArrayList ;


    }

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new ProductAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {

        holder.productName.setText(productArrayList.get(position).getProductName());
        holder.salesDate.setText(productArrayList.get(position).getSalesDate());
        holder.invoiceNo.setText(productArrayList.get(position).getInvoiceNo());
        holder.amount.setText(productArrayList.get(position).getAmount());


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView customerImage ;
        TextView salesDate , invoiceNo , productName, amount;


        public MyViewHolder(View itemView) {
            super(itemView);

            salesDate = (TextView) itemView.findViewById(R.id.date);
            invoiceNo = (TextView) itemView.findViewById(R.id.invoice_no);

            productName = (TextView) itemView.findViewById(R.id.product_name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
