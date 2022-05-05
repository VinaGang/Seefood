package com.example.seefood.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seefood.APIs.GoogleImageSearchAPI;
import com.example.seefood.R;
import com.example.seefood.models.SeeFoodMenu_Copy;
import com.google.android.material.tabs.TabLayout;
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

public class CreateMenuAdapter extends RecyclerView.Adapter<CreateMenuAdapter.MenuViewHolder> {

    private final Context context;
    private SeeFoodMenu_Copy menu;
    private static final String TAG = "CreateMenuAdapter";
    public CreateMenuAdapter(Context context, SeeFoodMenu_Copy menu) {
        this.context = context;
        this.menu = menu;
    }

    public void addAll(SeeFoodMenu_Copy menu){
        this.menu = menu;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateMenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_create_menu, parent, false);
        // Return a new holder instance
        return new CreateMenuAdapter.MenuViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateMenuAdapter.MenuViewHolder holder, int position) {
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
            tvNameDes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = GoogleImageSearchAPI.getSearchImageURL(name + " food");
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
                                for(int i=0; i<items.length(); i++){
                                    picList.add(items.getJSONObject(0).getString("link"));
                                    Log.d(TAG, picList.get(i));
                                }

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
            });
        }

    }

}
