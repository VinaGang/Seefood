package com.example.seefood.fragments;

import android.content.Intent;
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
import com.example.seefood.adapters.MenuAdapter;
import com.example.seefood.models.SeeFoodMenu;

import org.parceler.Parcels;

public class MenuFragment extends Fragment {
    public static final String TAG = "MenuFragment";
    private MenuAdapter menuAdapter;
    private RecyclerView rvMenu;
    SeeFoodMenu seeFoodMenu;
    private Toolbar tbFragMenu;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvMenu = view.findViewById(R.id.rvMenu);
        tbFragMenu = view.findViewById(R.id.tbFragMenu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tbFragMenu);

//        Intent i = view.getIntent();
        seeFoodMenu = new SeeFoodMenu();
        menuAdapter = new MenuAdapter(getContext(), seeFoodMenu);
        rvMenu.setAdapter(menuAdapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));

//        seeFoodMenu = (SeeFoodMenu) Parcels.unwrap(getParcelableExtra("menu"));
        menuAdapter.addAll(seeFoodMenu);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

}