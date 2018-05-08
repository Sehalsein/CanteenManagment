package com.sadaf.canteenmanagment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 08/05/18.
 */

public class MenuAdapter extends SectionedRecyclerViewAdapter<MenuAdapter.SubheaderViewHolder, MenuAdapter.ItemViewHolder> {

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private Context context;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser user;

    public MenuAdapter(List<MenuItem> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("shopping_cart");
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    public MenuAdapter() {
    }

    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {
        final MenuItem menuItem = menuItems.get(position);
        final MenuItem nextMenuItem = menuItems.get(position + 1);
        return !menuItem.getCategory().equals(nextMenuItem.getCategory());
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false));
    }

    @Override
    public SubheaderViewHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
    }

    @Override
    public void onBindItemViewHolder(final ItemViewHolder holder, final int itemPosition) {
        final MenuItem menuItem = menuItems.get(itemPosition);
        holder.setData(context,menuItem);
        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(menuItem);
            }
        });
    }

    private void addItem(MenuItem item){
        String key = mRef.push().getKey();
        OrderItem orderItem = new OrderItem(key,item.getName(),item.getPrice(),1);
        mRef.child(user.getUid()).child(key).setValue(orderItem);
        makeToast("Item added.");
    }

    private void makeToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderViewHolder subheaderHolder, int nextItemPosition) {
        final MenuItem menuItem = menuItems.get(nextItemPosition);
        subheaderHolder.setData(menuItem.getCategory());
    }

    @Override
    public int getItemSize() {
        return menuItems.size();
    }

    public static class SubheaderViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public SubheaderViewHolder(View itemView) {
            super(itemView);
            //Setup holder
            textView = itemView.findViewById(R.id.text_view);

        }

        public void setData(String data){
            textView.setText(data);
        }

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemPrice;
        private ImageView itemImage;
        private ImageButton addItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_image);
            addItem = itemView.findViewById(R.id.add_item);


        }

        public void setData(Context context, MenuItem data) {

            itemName.setText(data.getName());
            itemPrice.setText("$"+data.getPrice());
        }
    }
}
