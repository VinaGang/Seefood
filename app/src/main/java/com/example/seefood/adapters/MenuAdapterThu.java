package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.models.CartItem;
import com.example.seefood.models.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapterThu extends RecyclerView.Adapter<MenuAdapterThu.ViewHolder>{
    private Context context;
    private List<MenuItem> menuItems = new ArrayList<>();

    public static final String CART_ITEM_KEY = "cartItem";

    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(CART_ITEM_KEY);
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public MenuAdapterThu(Context context, List<MenuItem> menuItems){
        this.context = context;
        this.menuItems = menuItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMenuImage;
        private TextView tvMenuName;
        private TextView tvMenuPrice;
        private ImageView ivMenuAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenuImage = itemView.findViewById(R.id.ivMenuImage);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
            ivMenuAdd = itemView.findViewById(R.id.ivMenuAdd);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MenuAdapter", "onCreateViewHolder");
        View menuView = LayoutInflater.from(context).inflate(R.layout.menu_items,parent,false);
        return new MenuAdapterThu.ViewHolder(menuView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapterThu.ViewHolder holder, int position) {
        Log.d("MenuAdapter", "onBindViewHolder" + position);
        Glide.with(context).load(menuItems.get(position).getMenuImagePath()).into(holder.ivMenuImage);
        Log.i("Adapter", menuItems.get(position).getMenuName());
        holder.tvMenuName.setText(menuItems.get(position).getMenuName());
        holder.tvMenuPrice.setText("$"+Float.toString(menuItems.get(position).getMenuPrice()));

        holder.ivMenuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = currentUser.getUid();
                String keyID = ref.push().getKey();

                MenuItem item = menuItems.get(position);
                CartItem itemAdded = new CartItem(userId, item.getMenuImagePath(), item.getMenuName(), item.getMenuPrice(), 1);

                ref.child(keyID).setValue(itemAdded);

            }
        });
    }

/*    @Override
    protected void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position, @NonNull MenuItem item) {
        Log.d("MenuAdapter", "onBindViewHolder" + position);
        Glide.with(context).load(menuItems.get(position).getMenuImagePath()).into(holder.ivMenuImage);
        Log.i("Adapter", menuItems.get(position).getMenuName());
        holder.tvMenuName.setText(menuItems.get(position).getMenuName());
        holder.tvMenuPrice.setText("$"+Float.toString(menuItems.get(position).getMenuPrice()));

        holder.ivMenuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = currentUser.getUid();
                String keyID = ref.push().getKey();

                MenuItem item = menuItems.get(position);
                CartItem itemAdded = new CartItem(userId, item.getMenuImagePath(), item.getMenuName(), item.getMenuPrice(), 1);

                ref.child(keyID).setValue(itemAdded);

            }
        });
    }*/
    @Override
    public int getItemCount() {
        return menuItems.size();
    }


}
