/*
package com.example.seefood.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.models.Post;
import com.example.seefood.models.RestoMenu;
import com.example.seefood.models.User;
import com.example.seefood.statics.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import org.parceler.Parcels;

public class SavedMenuAdapter extends FirebaseRecyclerAdapter<RestoMenu, SavedMenuAdapter.ViewHolder> {

    private Context context;
    private FirebaseRecyclerOptions<RestoMenu> menus;

    public SavedMenuAdapter(Context context, FirebaseRecyclerOptions<RestoMenu> menus) {
        super(menus);
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull RestoMenu menu) {

        holder.menuTitle.setText(menu.name);
        setFadeAnimation(holder.itemView);

        holder.menuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new SavedMenuDetailFragment();

                Bundle args = new Bundle();
                args.putSerializable("menu", menu);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.flContainer, fragment).commit();
            }
        });
    }

    private void setFadeAnimation(View view) {

        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView menuCardView;
        private TextView menuTitle;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            menuTitle = itemView.findViewById(R.id.menuTitle);
            menuCardView = itemView.findViewById(R.id.menuCardView);
        }
    }
}
*/
