package com.sadaf.canteenmanagment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 08/05/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private Context context;


    public OrderAdapter() {
    }

    public OrderAdapter(List<OrderItem> orderItems, Context context) {
        this.orderItems = orderItems;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_shopping_cart, parent, false);
        return new OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderItem item = orderItems.get(position);
        OrderItemViewHolder viewHolder = (OrderItemViewHolder) holder;
        viewHolder.setRow(item);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }


    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private TextView itemQuantity;
        private TextView itemTotalPrice;


        public OrderItemViewHolder(View itemView) {
            super(itemView);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemTotalPrice = itemView.findViewById(R.id.item_total_price);
            itemName = itemView.findViewById(R.id.item_name);

        }

        public void setRow(OrderItem data) {
            itemPrice.setText("$"+data.getPrice());
            itemQuantity.setText("x"+data.getQuantity());
            itemTotalPrice.setText("$"+ data.getPrice() * data.getQuantity());
            itemName.setText(data.getName());
        }
    }
}
