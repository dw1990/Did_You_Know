package com.example.didyouknow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didyouknow.R;
import com.example.didyouknow.utility.DownloadImageTask;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private List<Favorite> data;

    public FavoriteAdapter(List<Favorite> data){
        this.data = data;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View newView = inflater.inflate(R.layout.favorite, parent, false);

        FavoriteViewHolder vh = new FavoriteViewHolder(newView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite fav = data.get(position);
        holder.name.setText(fav.name);
        holder.date.setText(fav.date);
        new DownloadImageTask(holder.image).execute(fav.imageURL);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
