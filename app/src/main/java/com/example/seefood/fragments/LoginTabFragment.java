package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seefood.MainActivity;
import com.example.seefood.R;

import org.w3c.dom.Text;

public class LoginTabFragment extends Fragment {

    public static final String LOGIN_KEY = "login";

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvForgot;
    private TextView tvGuest;
    private Button btnLogin;

    public LoginTabFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        tvForgot = view.findViewById(R.id.tvForgot);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvGuest = view.findViewById(R.id.tvGuest);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        tvGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }
}
