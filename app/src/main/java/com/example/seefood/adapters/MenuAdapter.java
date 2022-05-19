package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seefood.APIs.GoogleImageSearchAPI;
import com.example.seefood.R;
import com.example.seefood.models.CartItem;
import com.example.seefood.models.MenuItem;
import com.example.seefood.models.SeeFoodMenu_Copy;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<MenuItem> menuItems = new ArrayList<>();

    public static final String CART_ITEM_KEY = "cartItem";
    private static final String TAG = "MenuAdapter";
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(CART_ITEM_KEY);
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public MenuAdapter(Context context, List<MenuItem> menuItems){
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.menu_items, parent, false);
        // Return a new holder instance
        return new MenuAdapter.MenuViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        MenuItem menu = menuItems.get(position);

        holder.bind(menu, position);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMenuImage;
        private TextView tvMenuName;
        private TextView tvMenuPrice;
        private ImageView ivMenuAdd;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ivMenuImage = itemView.findViewById(R.id.ivMenuImage);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
            ivMenuAdd = itemView.findViewById(R.id.ivMenuAdd);
        }

        public void onItemImagePopupWindowClick(View view, String pictureURL) {
            Log.d(TAG, "OnItemImagePopup: " + pictureURL);
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_view_photo, null);
            ImageView ivPopupImage = popupView.findViewById(R.id.ivPopupImage);
            Glide.with(context).load(pictureURL).into(ivPopupImage);

            // create the popup window
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window token
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            // dismiss the popup window when touched


            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return false;
                }
            });
        }

        public void bind(MenuItem menuList, int position) {

            String url = GoogleImageSearchAPI.getSearchImageURL(menuList.getMenuName() + " food");
            Log.d(TAG, url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                    // Handle resulting parsed JSON response here

                    try {
                        List<String> picList = new ArrayList<>();
                        JSONArray items = response.getJSONArray("items");
                        for (int i = 0; i < items.length(); i++) {
                            picList.add(items.getJSONObject(0).getString("link"));
                            Log.d(TAG, picList.get(i));
                        }
                        if (!menuList.getMenuImagePath().equals(""))
                            Glide.with(context).load(picList.get(1))
                                    .error(R.drawable.ic_baseline_person_pin_24)
                                    .placeholder(R.drawable.ic_baseline_person_pin_24)
                                    .into(ivMenuImage);

                        ivMenuImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onItemImagePopupWindowClick(view, picList.get(1));
                            }
                        });
                        Log.i("Adapter", menuList.getMenuName());

                        tvMenuName.setText(menuList.getMenuName());
                        tvMenuPrice.setText("$" + Float.toString(menuList.getMenuPrice()));

                        ivMenuAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String userId = currentUser.getUid();
                                String keyID = ref.push().getKey();

                                MenuItem item = menuItems.get(position);
                                CartItem itemAdded = new CartItem(userId, picList.get(1), item.getMenuName(), item.getMenuPrice(), 1);

                                ref.child(keyID).setValue(itemAdded);

                            }
                        });

                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }




            });
        }

    }

}
