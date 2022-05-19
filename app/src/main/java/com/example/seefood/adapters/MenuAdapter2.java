/*
package com.example.seefood.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

public class MenuAdapter2 extends RecyclerView.Adapter<MenuAdapter2.ViewHolder>{
    private Context context;
    private List<MenuItem> menuItems = new ArrayList<>();

<<<<<<< HEAD
    private final Context context;
    private SeeFoodMenu_Copy menu;
    private List<String> foodName, foodPrice;
    public MenuAdapter2(Context context, SeeFoodMenu_Copy menu) {
        this.context = context;
        this.menu = menu;
        foodName = new ArrayList<>();
        foodPrice = new ArrayList<>();
=======
    public static final String CART_ITEM_KEY = "cartItem";

    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(CART_ITEM_KEY);
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public MenuAdapter(Context context, List<MenuItem> menuItems){
        this.context = context;
        this.menuItems = menuItems;
>>>>>>> 15c05873e4c649bd4b32a1653bdd62dedf58ae14
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
        return new MenuAdapter2.ViewHolder(menuView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("MenuAdapter", "onBindViewHolder" + position);
        Glide.with(context).load(menuItems.get(position).getMenuImagePath()).into(holder.ivMenuImage);
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
    @Override
    public int getItemCount() {
        return menuItems.size();
    }

<<<<<<< HEAD
    public List<String> getFoodNames(){
        return foodName;
    }
    public List<String> getFoodPrice(){
        return foodPrice;
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
=======
>>>>>>> 15c05873e4c649bd4b32a1653bdd62dedf58ae14

}
*/
