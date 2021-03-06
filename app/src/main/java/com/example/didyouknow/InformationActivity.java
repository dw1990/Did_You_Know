package com.example.didyouknow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didyouknow.utility.DownloadImageTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import cz.msebera.android.httpclient.Header;

public class InformationActivity extends Activity {

    private static final String ENTITY_ENDPOINT = "http://dhbw.phillippm.de/api/v1/entities/";
    private TextView txt_key1;
    private TextView txt_key2;
    private TextView txt_key3;
    private TextView txt_key4;
    private TextView txt_value1;
    private TextView txt_value2;
    private TextView txt_value3;
    private TextView txt_value4;
    private TextView title;
    private ImageView img;
    private TextView txt_background;

    private boolean isFavorite = false;
    private String id;
    private Button likeButton;

    private JSONArray infoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_information);

        img = (ImageView) findViewById(R.id.img_preview);
        title = (TextView) findViewById(R.id.txt_Name);

        txt_key1 = (TextView) findViewById(R.id.txt_key1);
        txt_key2 = (TextView) findViewById(R.id.txt_key2);
        txt_key3 = (TextView) findViewById(R.id.txt_key3);
        txt_key4 = (TextView) findViewById(R.id.txt_key4);

        txt_value1 = (TextView) findViewById(R.id.txt_value1);
        txt_value2 = (TextView) findViewById(R.id.txt_value2);
        txt_value3 = (TextView) findViewById(R.id.txt_value3);
        txt_value4 = (TextView) findViewById(R.id.txt_value4);

        txt_background = (TextView) findViewById(R.id.txt_background);

        likeButton = findViewById(R.id.btn_like);

        id = getIntent().getStringExtra("object_id");

        fetchData(id);
    }

    private void fetchData(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ENTITY_ENDPOINT + id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                try {
                    txt_background.setText(object.getString("backgroundText"));
                    JSONArray array = object.getJSONArray("data");

                    txt_key1.setText(array.getJSONObject(0).getString("key"));
                    txt_key2.setText(array.getJSONObject(1).getString("key"));
                    txt_key3.setText(array.getJSONObject(2).getString("key"));
                    txt_key4.setText(array.getJSONObject(3).getString("key"));

                    txt_value1.setText(array.getJSONObject(0).getString("value"));
                    txt_value2.setText(array.getJSONObject(1).getString("value"));
                    txt_value3.setText(array.getJSONObject(2).getString("value"));
                    txt_value4.setText(array.getJSONObject(3).getString("value"));

                    infoData = object.getJSONArray("infoData");

                    title.setText(object.getString("title"));

                    boolean fav = object.getBoolean("isFavorite");
                    if (fav) {
                        likeButton.setBackground(getResources().getDrawable(R.drawable.heart));
                    } else {
                        likeButton.setBackground(getResources().getDrawable(R.drawable.heart_not));
                    }

                    InformationActivity.this.isFavorite = fav;

                    new DownloadImageTask(img).execute(object.getString("imageURL"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onLike(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        String endpoint = "http://dhbw.phillippm.de/api/v1/favorites/entities/" + id;

        if (this.isFavorite) {
            client.delete(endpoint, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    likeButton.setBackground(getResources().getDrawable(R.drawable.heart_not));
                    InformationActivity.this.isFavorite = false;
                    Toast.makeText(InformationActivity.this, "Aus den Favoriten entfernt.", (int) 3).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //TODO
                }
            });
        } else {
            client.post(endpoint, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    likeButton.setBackground(getResources().getDrawable(R.drawable.heart));
                    InformationActivity.this.isFavorite = true;
                    Toast.makeText(InformationActivity.this, "Zu den Favoriten hinzugefügt.", (int) 3).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //TODO
                }
            });
        }
    }

    public void onOpenInfos(View view) throws JSONException {
        AlertDialog alertDialog = new AlertDialog.Builder(InformationActivity.this).create();
        alertDialog.setTitle(title.getText() + ": Informationen");

        String data = "";
        for (int i = 0; i < infoData.length(); i++) {
            data = data + "- " + infoData.getJSONObject(i).getString("value") + "\n\r";
        }

        alertDialog.setMessage("Möglichkeiten vor Ort: \n\r" + data);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Alles klar.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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