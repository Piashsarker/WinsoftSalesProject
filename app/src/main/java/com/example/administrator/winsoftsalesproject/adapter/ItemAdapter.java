package com.example.administrator.winsoftsalesproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.winsoftsalesproject.R;
import com.example.administrator.winsoftsalesproject.model.Item;

import java.util.ArrayList;

import static com.example.administrator.winsoftsalesproject.R.id.txt_minimum_unit;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemCode.setText(itemArrayList.get(position).getItemCode());
        holder.itemName.setText(itemArrayList.get(position).getItemName());
        holder.saleQuantity.setText(itemArrayList.get(position).getQuantity());
        holder.mUnit.setText(itemArrayList.get(position).getMinimumUnit());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemCode , itemName , saleQuantity , mUnit;
        ImageButton btnDelete ;
        public ViewHolder(View itemView) {
            super(itemView);

            itemCode = (TextView) itemView.findViewById(R.id.txt_item_code);
            itemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            saleQuantity = (TextView) itemView.findViewById(R.id.txt_sale_quantity);
            mUnit = (TextView) itemView.findViewById(txt_minimum_unit);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);


        }
    }
}
