package com.example.didyouknow.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didyouknow.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;
    public TextView date;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.img_fav);
        name = itemView.findViewById(R.id.txt_fav_name);
        date = itemView.findViewById(R.id.txt_fav_date);
    }
}
