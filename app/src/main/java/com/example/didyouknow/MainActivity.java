package com.example.didyouknow;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.didyouknow.utility.OnSwipeTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        findViewById(R.id.swipe_container).setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeRight() {
                navigateToCameraActivity();
            }

            @Override
            public void onSwipeLeft() {
                navigateToFavoritesActivity();
            }
        });

        }
    public void navigateToCamera(View view){
        this.navigateToCameraActivity();
    }

    public void navigateToFavoritesActivity (View view){
        this.navigateToFavoritesActivity();
    }

    private void navigateToCameraActivity(){
        Intent intent = new Intent(this, ImageAnalysisActivity.class);
        startActivity(intent);
    }

    private void navigateToFavoritesActivity(){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}