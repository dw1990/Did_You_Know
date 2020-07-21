package com.example.didyouknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.didyouknow.adapters.Favorite;
import com.example.didyouknow.adapters.FavoriteAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        final List<Favorite> favorites = new ArrayList<Favorite>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://dhbw.phillippm.de/api/v1/favorites", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Favorite fav = new Favorite(obj.getString("imageURL"),
                                obj.getString("name"),
                                obj.getString("date"),
                                obj.getString("id"));
                        favorites.add(fav);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(favorites, FavoritesActivity.this);

                RecyclerView recyclerView = findViewById(R.id.rec_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));
                recyclerView.setAdapter(favoriteAdapter);


            }
        });
    }

    public void onGoFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }


    public void onGoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onGoExplore(View view) {
        Intent intent = new Intent(this, ImageAnalysisActivity.class);
        startActivity(intent);
    }
}