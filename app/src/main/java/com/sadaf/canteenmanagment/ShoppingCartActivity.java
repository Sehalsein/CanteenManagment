package com.sadaf.canteenmanagment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ShoppingCartActivity extends AppCompatActivity {


    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef;
    final int GET_NEW_CARD = 2;

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private FirebaseUser user;

    private TextView totalCost;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        totalCost = findViewById(R.id.total_amount_text_view);

        mRef = mDatabase.getReference("shopping_cart");
        user = FirebaseAuth.getInstance().getCurrentUser();


        loadOrderItem();
    }

    private void updateAmount(){
        Double total = 0.00;


        for(OrderItem item: orderItems){
            total += item.getPrice() * item.getQuantity();
        }

        totalCost.setText("$"+total);
    }




    private void loadOrderItem(){

        mRef.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                orderItems.add(dataSnapshot.getValue(OrderItem.class));
                adapter = new OrderAdapter(orderItems,ShoppingCartActivity.this);
                recyclerView.setAdapter(adapter);
                updateAmount();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void payAmount(View view){


        Intent intent = new Intent(ShoppingCartActivity.this, CardEditActivity.class);
        startActivityForResult(intent,GET_NEW_CARD);
    }


    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            // Your processing goes here.

            dialog = new MaterialDialog.Builder(this)
                    .title("Payment in progress")
                    .content("Please wait.")
                    .progress(true, 0)
                    .show();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();
                    dialog.dismiss();
                    mRef.setValue(null);

                    ShoppingCartActivity.this.finish();
                }
            }, 3000);
        }
    }

}
