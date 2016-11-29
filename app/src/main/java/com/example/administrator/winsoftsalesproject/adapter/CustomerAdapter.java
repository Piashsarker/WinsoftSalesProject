package com.example.administrator.winsoftsalesproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.Customer;

import java.util.ArrayList;

/**
 * Created by pt on 11/5/16.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private ArrayList<Customer> customerArrayList;
    private Context context ;
    private onLongItemClickListener onLongItemClickListener;



    public CustomerAdapter(Context context , ArrayList<Customer> customerArrayLists){
         this.context = context ;
         this.customerArrayList = customerArrayLists ;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.customerName.setText(customerArrayList.get(position).getCustomerName());
        holder.customerAddress.setText(customerArrayList.get(position).getAddress());
        holder.phoneNumber.setText(customerArrayList.get(position).getEmail());
        // Glide.with(context).load(customerArrayList.get(position).getCustomerId()).into(holder.customerImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Long Press To Add", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }
    public void setFilter(ArrayList<Customer> contactListModel){
        customerArrayList = new ArrayList<>();
        customerArrayList.addAll(contactListModel);
        notifyDataSetChanged();

    }

    public void setOnLongItemClickListener(final onLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;

    }

    public interface onLongItemClickListener {
        void onLongItemClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView customerImage ;
        TextView customerName , phoneNumber , customerAddress;
        View cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            customerImage = (ImageView) itemView.findViewById(R.id.customer_image);
            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            phoneNumber = (TextView) itemView.findViewById(R.id.phone_number);
            customerAddress = (TextView) itemView.findViewById(R.id.txt_address);

            cardView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
         if(onLongItemClickListener!=null){
              onLongItemClickListener.onLongItemClick(v, getAdapterPosition());
         }
            return false;
        }
    }
}
