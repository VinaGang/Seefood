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

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.models.CartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartAdapter extends FirebaseRecyclerAdapter<CartItem, CartAdapter.ViewHolder> {
    private Context context;
    private FirebaseRecyclerOptions<CartItem> cartItems;
    public static final String CART_ITEM_KEY = "cartItem";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(CART_ITEM_KEY);

    public CartAdapter(Context context, FirebaseRecyclerOptions<CartItem> cartItems){
        super(cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivFoodImage;
        private TextView tvFoodName;
        private TextView tvPrice;
        private TextView tvAmount;
        private ImageView ivAddBtn;
        private ImageView ivMinusBtn;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);;
            tvPrice = itemView.findViewById(R.id.tvPrice);;
            tvAmount = itemView.findViewById(R.id.tvAmount);;
            ivAddBtn = itemView.findViewById(R.id.ivAddBtn);;
            ivMinusBtn = itemView.findViewById(R.id.ivMinusBtn);;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull CartItem cartItem) {
        Log.d("CartAdapter", "onBindViewHolder" + position);
        Glide.with(context).load(cartItem.getFoodImagePath()).into(holder.ivFoodImage);
        holder.tvFoodName.setText(cartItem.getFoodName());
        holder.tvPrice.setText("$"+Float.toString(cartItem.getPrice()));
        holder.tvAmount.setText(Integer.toString(cartItem.getAmount()));

        holder.ivAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentAmount = cartItem.getAmount();
                ref.child(getRef(position).getKey()).child("amount").setValue(currentAmount+1);
            }
        });

        holder.ivMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentAmount = cartItem.getAmount();
                if(currentAmount > 0)
                ref.child(getRef(position).getKey()).child("amount").setValue(currentAmount-1);
            }
        });


    }

}
