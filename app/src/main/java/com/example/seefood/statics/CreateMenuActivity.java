package com.example.seefood.statics;


import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.seefood.R;
import com.example.seefood.adapters.MenuAdapter;
import com.example.seefood.models.Food;
import com.example.seefood.models.RestoMenu;
import com.example.seefood.models.SeeFoodMenu_Copy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateMenuActivity extends AppCompatActivity {

    public static final String TAG = "CreateMenuActivity";
    private MenuAdapter menuAdapter;
    private RecyclerView rvMenu;
    SeeFoodMenu_Copy seeFoodMenu;
    private Toolbar tbCreateMenu;
    private DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("menu");
    private EditText menuTitle;
    List<String> foodName, foodPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        rvMenu = findViewById(R.id.rvMenu);
        tbCreateMenu = findViewById(R.id.tbCreateMenu);
        setSupportActionBar(tbCreateMenu);
        tbCreateMenu.setNavigationOnClickListener(view -> {
            finish();
        });

        Intent i = getIntent();
        seeFoodMenu = new SeeFoodMenu_Copy();
        menuAdapter = new MenuAdapter(this, seeFoodMenu);
        rvMenu.setAdapter(menuAdapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        seeFoodMenu = (SeeFoodMenu_Copy) Parcels.unwrap(i.getParcelableExtra("menu"));
        menuAdapter.addAll(seeFoodMenu);

        //grab the adapter's data
        foodName = menuAdapter.getFoodNames();
        foodPrice = menuAdapter.getFoodPrice();

        //grab the menu title
        menuTitle = findViewById(R.id.etMenuName);
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

                if(menuTitle.getText().toString().equals("")){
                    Toast.makeText(this, "Please name the menu you want to save", Toast.LENGTH_LONG).show();
                }
                else {
                    List<Food> foodInMenu = new ArrayList<>();

                    //get all the food
                    for (int i = 0; i < foodName.size(); i++) {
                        Food food = new Food(foodName.get(i), "", foodPrice.get(i));
                        foodInMenu.add(food);
                    }

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String strDate = dateFormat.format(date);

                    //generate the menu
                    RestoMenu menu = new RestoMenu(menuTitle.getText().toString(), foodInMenu, strDate);

                    for (int i = 0; i < menu.items.size(); i++)
                        Log.i(TAG, menu.items.get(i).name);

                    firebaseDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(menuTitle.getText().toString()).setValue(menu)
                            .addOnSuccessListener(unused -> Log.i(TAG, "Menu posted!"))
                            .addOnFailureListener(e -> Log.i(TAG, "Menu not posted"));
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}