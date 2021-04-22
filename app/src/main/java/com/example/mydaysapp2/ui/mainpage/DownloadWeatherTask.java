package com.example.mydaysapp2.ui.mainpage;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mydaysapp2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadWeatherTask extends AsyncTask<String,Void,String> {
    private String temp;
    private View view;

    @Override
    protected String doInBackground(String... urls) {
        String json = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();

            while(data != -1) { //-1 to wartość end of file
                char letter = (char) data;
                json += letter;
                data = reader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(String json) {  //wywoła się po skończeniu pobierania danych
        super.onPostExecute(json);

        try {
            JSONObject jsonObject = new JSONObject(json);
            String weatherInfo = jsonObject.getString("main");

            JSONObject pogoda = new JSONObject(weatherInfo);
            temp = pogoda.getString("temp");

          TextView tempTV = view.findViewById(R.id.tempTv);
          tempTV.setText(temp+" °C");



        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
//dziwna praktyka ale nie miałem innego pomysłu
    public void setView(View view){
        this.view = view;
    }
}
