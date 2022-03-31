package com.example.seefood;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.seefood.fragments.LoginTabFragment;
import com.example.seefood.fragments.SignupTabFragment;

public class LoginAdapter extends FragmentStateAdapter {

    public LoginAdapter(FragmentManager fragment, Lifecycle lifecycle){
        super(fragment, lifecycle);
    }

    public Fragment createFragment(int position){
        if(position == 1){
            return new SignupTabFragment();
        }
        return new LoginTabFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
