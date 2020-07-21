package com.example.didyouknow.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didyouknow.InformationActivity;
import com.example.didyouknow.R;
import com.example.didyouknow.utility.DownloadImageTask;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private List<Favorite> data;
    private Activity callingActivity;

    public FavoriteAdapter(List<Favorite> data, Activity callingActivity){
        this.data = data;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View newView = inflater.inflate(R.layout.favorite, parent, false);

        final FavoriteViewHolder vh = new FavoriteViewHolder(newView);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteAdapter.this.callingActivity, InformationActivity.class);
                intent.putExtra("object_id", vh.id);

                FavoriteAdapter.this.callingActivity.startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        Favorite fav = data.get(position);
        holder.name.setText(fav.name);
        holder.date.setText(fav.date);
        holder.id = fav.id;
        new DownloadImageTask(holder.image).execute(fav.imageURL);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
