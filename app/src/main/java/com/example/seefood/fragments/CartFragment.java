package com.example.seefood.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seefood.R;
import com.example.seefood.adapters.CartAdapter;
import com.example.seefood.models.CartItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartFragment extends Fragment {

    private CartAdapter cartAdapter;
    float total_price = 0;
    
    FirebaseRecyclerOptions<CartItem> options;
    public static final String CART_ITEM_KEY = "cartItem";

    TextView tvTotalAmount;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(CART_ITEM_KEY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvCartItems = view.findViewById(R.id.rvCartItems);
        options = new FirebaseRecyclerOptions.Builder<CartItem>().setQuery(ref, CartItem.class).build();
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);

        //Create adapter
        cartAdapter = new CartAdapter(getContext(), options);

        //Set adapter on the recycler view
        rvCartItems.setAdapter(cartAdapter);

        cartAdapter.notifyDataSetChanged();

        // Set a layout Manager on the recycler view
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartItem cartItem = dataSnapshot.getValue(CartItem.class);
                    total_price += cartItem.getPrice()*cartItem.getAmount();
                }
                tvTotalAmount.setText("$"+Float.toString(total_price));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        cartAdapter.stopListening();
    }
}