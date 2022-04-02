package com.example.seefood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seefood.statics.MainActivity;
import com.example.seefood.R;
import com.example.seefood.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupTabFragment extends Fragment {
    public static final String LOGIN_KEY = "signup";
    private static final String TAG = "SignupTabFragment";

    private EditText etEmail;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private User user;

    public SignupTabFragment() {
    };

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

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("user");
        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Email or password cannot be empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etNewPassword.getText().toString() != password) {
                    Toast.makeText(getContext(), "Password doesn't match! Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                user = new User(email, password);
                registerUser(email, password);
            }
        });
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyID = mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(user);
        Intent login = new Intent(getContext(), MainActivity.class);
        startActivity(login);
    }
}