package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seefood.statics.MainActivity;
import com.example.seefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment extends Fragment {

    public static final String LOGIN_KEY = "login";

    private EditText etuserEmail;
    private EditText etPassword;
    private TextView tvForgot;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    //Empty constructor
    public LoginTabFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etuserEmail = view.findViewById(R.id.etuserEmail);
        etPassword = view.findViewById(R.id.etPassword);
        tvForgot = view.findViewById(R.id.tvForgot);
        btnLogin = view.findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etuserEmail.getText().toString();
                String password = etPassword.getText().toString();

                loginUserAccount(email, password);

            }
        });

    }

    private void loginUserAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
