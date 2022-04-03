package com.example.seefood.statics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.seefood.R;
import com.example.seefood.adapters.LoginAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tlTabs;
    private ViewPager2 vpViewPage;
    private LoginAdapter loginadapter = new LoginAdapter(getSupportFragmentManager(),getLifecycle());
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        tlTabs = findViewById(R.id.tlTabs);
        vpViewPage = findViewById(R.id.vpViewPage);

        tlTabs.addTab(tlTabs.newTab().setText("Log In"));
        tlTabs.addTab(tlTabs.newTab().setText("Sign Up"));


        vpViewPage.setAdapter(loginadapter);
        tlTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("LoginActivity", "Tab is selected!");
                vpViewPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tlTabs.selectTab(tlTabs.getTabAt(position));
            }
        });
    }
}