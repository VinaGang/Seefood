package com.example.seefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seefood.R;
import com.example.seefood.models.SeeFoodMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final Context context;
    private SeeFoodMenu menu;
    public MenuAdapter(Context context, SeeFoodMenu menu) {
        this.context = context;
        this.menu = menu;
    }

    public void addAll(SeeFoodMenu menu){
        this.menu = menu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_menu, parent, false);
        // Return a new holder instance
        return new MenuAdapter.MenuViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        List<List<String>> menuList = menu.getMenu();
        holder.bind(menuList, position);
    }

    @Override
    public int getItemCount() {
        if(menu == null || menu.getMenu() == null || menu.getMenu().isEmpty()) return 0;
        return menu.getMenu().get(2).size();
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
                des = "unknown";
            }else{
                des = menuList.get(1).get(position);
            }
            if(position >= menuList.get(2).size()) {
                price = "NA";
            }else{
                price = menuList.get(2).get(position);
            }
            tvNameDes.setText(name +"\n"+ des);
            tvPrice.setText(price);
        }

    }

}
