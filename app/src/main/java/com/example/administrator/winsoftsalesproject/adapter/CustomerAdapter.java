package com.example.administrator.winsoftsalesproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.Customer;

import java.util.ArrayList;

/**
 * Created by pt on 11/5/16.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private ArrayList<Customer> customerArrayList;
    private Context context ;



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

        holder.customerName.setText(customerArrayList.get(position).getName());
        holder.customerAddress.setText(customerArrayList.get(position).getAddress());
        holder.phoneNumber.setText(customerArrayList.get(position).getDesignation());
        Glide.with(context).load(customerArrayList.get(position).getPhoto()).into(holder.customerImage);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ArrayList<String> entrys = new ArrayList<String>();
                entrys.add(context.getString(R.string.dialog_add));

                final CharSequence[] items = entrys.toArray(new CharSequence[entrys.size()]);


                // File delete confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.dialog_title_choose));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            sendResultBack( holder.getAdapterPosition());
                        }
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton(context.getString(R.string.dialog_action_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Long Press To Add", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendResultBack(int adapterPosition) {

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
