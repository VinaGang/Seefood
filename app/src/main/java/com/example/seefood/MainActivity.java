package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sampleBtn = findViewById(R.id.sampleBtn);

        sampleBtn.setOnClickListener(view -> {

            Intent i = new Intent(this, TestActivity.class);
            startActivity(i);
        });
    }
}