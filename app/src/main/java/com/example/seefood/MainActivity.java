package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.fragments.CameraFragment;
import com.example.seefood.fragments.CartFragment;
import com.example.seefood.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Begin the transaction
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener (item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_camera:
                    fragment = new CameraFragment();
                    break;
                case R.id.action_cart:
                default:
                    fragment = new CartFragment();
                    break;
            };

            fragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.flContainer, fragment)
                    .commit();

            return true;
        });

        //set default
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}