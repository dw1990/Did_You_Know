package com.example.didyouknow.utility.asnyctasks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.didyouknow.InformationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class StartAnalysisTask extends AsyncTask<Bitmap, Void, String> {


    private OnTaskCompleted callback;

    public StartAnalysisTask(OnTaskCompleted callback){

        this.callback = callback;
    }

    private static final String ANALYSIS_ENDPOINT = "http://dhbw.phillippm.de/api/v1/classify";
    @Override
    protected String doInBackground(Bitmap... bitmaps) {
        Log.d("abc","def");
        Bitmap map = bitmaps[0];
        String ret = "-1";

        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter("Content-Type", "multipart/form-data");

        HttpPost post = new HttpPost(ANALYSIS_ENDPOINT);

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        entity.addPart("image", new ByteArrayBody(data, "image/jpg", "image.jpg"));
        post.setEntity(entity.build());

        try {
            HttpResponse response = client.execute(post);
            String json = EntityUtils.toString(response.getEntity());

            JSONObject jsonObject = new JSONObject(json);
            ret = jsonObject.getString("id");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        this.callback.OnTaskCompleted(s);
    }
}
