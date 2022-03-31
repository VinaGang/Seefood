package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seefood.R;

import org.w3c.dom.Text;

public class SignupTabFragment extends Fragment {
    public static final String LOGIN_KEY = "signup";

    private EditText etEmail;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnSignup;

    public SignupTabFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = view.findViewById(R.id.etEmail);
        etNewUsername = view.findViewById(R.id.etNewUsername);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnSignup = view.findViewById(R.id.btnSignup);
    }
}
