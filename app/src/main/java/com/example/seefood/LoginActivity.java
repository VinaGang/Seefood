package com.example.seefood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tlTabs;
    private ViewPager2 vpViewPage;
    private LoginAdapter loginadapter = new LoginAdapter(getSupportFragmentManager(),getLifecycle());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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