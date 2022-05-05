package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seefood.R;
import com.example.seefood.models.Food;
import com.example.seefood.models.SeeFoodMenu;
import com.example.seefood.models.SeeFoodMenu_Copy;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedMenuDetailAdapter extends RecyclerView.Adapter<SavedMenuDetailAdapter.MenuViewHolder> {

    private final Context context;
    private List<Food> items;

    public SavedMenuDetailAdapter(Context context, List<Food> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SavedMenuDetailAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_menu, parent, false);
        // Return a new holder instance
        return new SavedMenuAdapter.MenuViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedMenuDetailAdapter.MenuViewHolder holder, int position) {
        List<List<String>> menuList = menu.getMenu();
        holder.bind(menuList, position);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameDes, tvPrice;

        public MenuViewHolder(View view) {
            super(view);
            tvNameDes = view.findViewById(R.id.tvNameDes);
            tvPrice = view.findViewById(R.id.tvPrice);
        }

        public void bind(List<List<String>> menuList, int position) {
            String name;
            String des;
            String price;
            if(position >= menuList.get(0).size()) {
                name = "unknown";
            }else{
                name = menuList.get(0).get(position);
            }
            if(position >= menuList.get(1).size()) {
                price = "unknown";
            }else{
                price = menuList.get(1).get(position);
            }
//            if(position >= menuList.get(2).size()) {
//                price = "NA";
//            }else{
//                price = menuList.get(2).get(position);
//            }
            tvNameDes.setText(name);
            tvPrice.setText(price);

            foodName.add(name);
            foodPrice.add(price);
        }

    }

}
