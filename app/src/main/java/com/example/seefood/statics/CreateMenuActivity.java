package com.example.seefood.statics;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.seefood.R;
import com.example.seefood.adapters.MenuAdapter;
import com.example.seefood.models.SeeFoodMenu;
import com.example.seefood.models.SeeFoodMenu_Copy;

import org.parceler.Parcels;

public class CreateMenuActivity extends AppCompatActivity {

    public static final String TAG = "CreateMenuActivity";
    private MenuAdapter menuAdapter;
    private RecyclerView rvMenu;
    SeeFoodMenu_Copy seeFoodMenu;
    private Toolbar tbCreateMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        rvMenu = findViewById(R.id.rvMenu);
        tbCreateMenu = findViewById(R.id.tbCreateMenu);
        setSupportActionBar(tbCreateMenu);

        Intent i = getIntent();
        seeFoodMenu = new SeeFoodMenu_Copy();
        menuAdapter = new MenuAdapter(this, seeFoodMenu);
        rvMenu.setAdapter(menuAdapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        seeFoodMenu = (SeeFoodMenu_Copy) Parcels.unwrap(i.getParcelableExtra("menu"));
        menuAdapter.addAll(seeFoodMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.actionSaveMenu:
                Log.d(TAG, "actionSaveMenu");
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}