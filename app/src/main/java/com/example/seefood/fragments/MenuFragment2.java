/*
package com.example.seefood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.adapters.SavedMenuAdapter;
import com.example.seefood.models.RestoMenu;
//import com.example.seefood.models.SeeFoodMenu;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuFragment2 extends Fragment {

    public static final String TAG = "MenuFragment";
    DatabaseReference menuRef;
    FirebaseRecyclerOptions<RestoMenu> options;
    protected SavedMenuAdapter menuAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Toolbar tbFragMenu = view.findViewById(R.id.tbSavedMenu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tbFragMenu);

        //RecyclerView rvMenuList = view.findViewById(R.id.rvMenuList);

        menuRef = FirebaseDatabase.getInstance().getReference("menu").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        options = new FirebaseRecyclerOptions.Builder<RestoMenu>()
                .setQuery(menuRef, RestoMenu.class)
                .build();

        menuAdapter = new SavedMenuAdapter(getContext(), options);

       // rvMenuList.setAdapter(menuAdapter);
        //rvMenuList.setLayoutManager(new LinearLayoutManager(getContext()));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        menuAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        menuAdapter.stopListening();
    }

}*/
