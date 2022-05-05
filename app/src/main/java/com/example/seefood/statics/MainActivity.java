package com.example.seefood.statics;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.fragments.CameraFragment;
import com.example.seefood.fragments.CartFragment;
import com.example.seefood.fragments.HomeFragment;
import com.example.seefood.fragments.MenuFragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Begin the transaction
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public static final int FRAG_REQUEST = 1;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

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
                case R.id.action_seefoodMenu:
                    fragment = new MenuFragment2();
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

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}