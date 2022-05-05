package com.example.seefood.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seefood.R;
import com.example.seefood.adapters.CartAdapter;
import com.example.seefood.adapters.MenuAdapter;
import com.example.seefood.models.CartItem;
import com.example.seefood.models.MenuItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    private MenuAdapter menuAdapter;
    List<MenuItem> menuItems = new ArrayList<>();
    MenuItem menuTrial = new MenuItem("https://delightfulplate.com/wp-content/uploads/2020/09/Vietnamese-Crepe-Banh-Xeo-featured.jpg",
            "Banh Xeo", 9);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvMenuItems = view.findViewById(R.id.rvMenuItems);
        menuItems.add(menuTrial);

        //Create adapter
        menuAdapter = new MenuAdapter(getContext(), menuItems);

        //Set adapter on the recycler view
        rvMenuItems.setAdapter(menuAdapter);

        menuAdapter.notifyDataSetChanged();

        // Set a layout Manager on the recycler view
        rvMenuItems.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}