package com.example.seefood.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seefood.R;
import com.example.seefood.adapters.CartAdapter;
import com.example.seefood.models.CartItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    List<CartItem> cartItems;
    public static final String FIREBASE_URL = "https://seefood-60e84-default-rtdb.firebaseio.com/cartItem";

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
        cartItems = new ArrayList<>();

        //Create adapter
        CartAdapter movieAdapter = new CartAdapter(getContext(), cartItems);

        //Set adapter on the recycler view
        rvCartItems.setAdapter(movieAdapter);

        // Set a layout Manager on the recycler view
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(FIREBASE_URL);


    }
}