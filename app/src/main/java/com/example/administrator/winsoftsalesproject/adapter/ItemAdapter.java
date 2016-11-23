package com.example.administrator.winsoftsalesproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.Item;

import java.util.ArrayList;

/**
 * Created by pt on 11/10/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context ;
    private ArrayList<Item> itemArrayList ;

    public ItemAdapter(Context context , ArrayList<Item> itemArrayList){
        this.context = context ;
        this.itemArrayList = itemArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.date.setText(itemArrayList.get(position).getDate());
        holder.itemCode.setText(itemArrayList.get(position).getItemCode());
        holder.cutomerCode.setText(itemArrayList.get(position).getCustomerCode());
        holder.amount.setText(itemArrayList.get(position).getBalance());

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date , cutomerCode , itemCode , amount;
        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            cutomerCode = (TextView) itemView.findViewById(R.id.invoice_no);
            itemCode = (TextView) itemView.findViewById(R.id.product_name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
