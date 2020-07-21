package com.example.didyouknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.didyouknow.utility.FunFacts;
import com.example.didyouknow.utility.asnyctasks.OnTaskCompleted;
import com.example.didyouknow.utility.asnyctasks.StartAnalysisTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.protocol.HttpRequestHandler;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ImageAnalysisActivity extends Activity implements OnTaskCompleted<String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_analysis);

        TextView txt = (TextView) findViewById(R.id.txtRandomFact);
        txt.setText(FunFacts.getRandomFact());

        startCamera();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            try {
                ImageView view = (ImageView) findViewById(R.id.img_container);
                view.setImageBitmap(bitmap);

                startAnalysis(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startAnalysis(Bitmap map){
        new StartAnalysisTask(this).execute(map);
    }

    private void startCamera(){
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureImageIntent, 1);
    }

    @Override
    public void OnTaskCompleted(String content) {

        if(content.equals("null")){

            startCamera();
        }

        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("object_id", content);
        startActivity(intent);
    }
}

