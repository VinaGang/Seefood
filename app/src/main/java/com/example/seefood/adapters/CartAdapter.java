package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.models.CartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class CartAdapter extends FirebaseRecyclerAdapter<CartAdapter.ViewHolder> {
    Context context;
    private FirebaseRecyclerOption <CartItem> cartItems;

    public CartAdapter(Context context, FirebaseRecyclerOption<CartItem> cartItems){
        super(cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFoodImage;
        TextView tvFoodName;
        TextView tvPrice;
        TextView tvAmount;
        Button btnAdd;
        Button btnSub;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);;
            tvPrice = itemView.findViewById(R.id.tvPrice);;
            tvAmount = itemView.findViewById(R.id.tvAmount);;
            btnAdd = itemView.findViewById(R.id.btnAdd);;
            btnSub = itemView.findViewById(R.id.btnSub);;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CartAdapter", "onCreateViewHolder");
        View cartView = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new ViewHolder(cartView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
