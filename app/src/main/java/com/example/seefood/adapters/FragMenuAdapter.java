package com.example.seefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.models.SeeFoodMenu_Copy;

import java.util.List;

public class FragMenuAdapter extends RecyclerView.Adapter<FragMenuAdapter.MenuViewHolder> {

    private final Context context;
    private SeeFoodMenu_Copy menu;
    public FragMenuAdapter(Context context, SeeFoodMenu_Copy menu) {
        this.context = context;
        this.menu = menu;
    }

    public void addAll(SeeFoodMenu_Copy menu){
        this.menu = menu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FragMenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_frag_menu, parent, false);
        // Return a new holder instance
        return new FragMenuAdapter.MenuViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull FragMenuAdapter.MenuViewHolder holder, int position) {
        List<List<String>> menuList = menu.getMenu();
        holder.bind(menuList, position);
    }

    @Override
    public int getItemCount() {
        if(menu == null || menu.getMenu() == null || menu.getMenu().isEmpty()) return 0;
        return menu.getMenu().get(1).size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameDes, tvPrice;
        public Button btAdd;

        public MenuViewHolder(View view) {
            super(view);
            tvNameDes = view.findViewById(R.id.tvNameDes);
            tvPrice = view.findViewById(R.id.tvPrice);
            btAdd = view.findViewById(R.id.btAdd);
        }

        public void bind(List<List<String>> menuList, int position) {
            String name;
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
            tvNameDes.setText(name);
            tvPrice.setText(price);
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                    //Save to Cart

                }
            });
        }

    }

}
