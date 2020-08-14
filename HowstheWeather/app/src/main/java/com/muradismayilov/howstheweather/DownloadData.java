package com.muradismayilov.howstheweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadData extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        URL url;
        HttpsURLConnection httpsURLConnection;

        try{
            url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data > 0){

                char c = (char) data;
                result += c;

                data = inputStreamReader.read();
            }

            return result;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String name = jsonObject.getString("name");
                int id = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
                int temp = (int) Math.rint(jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
                String main = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                WeatherActivity.nameTV.setText(name);
                WeatherActivity.mainTV.setText(main);
                WeatherActivity.descriptionTV.setText(description);
                WeatherActivity.tempTV.setText(temp + "°");

                if(id >= 0 && id < 300){
                    WeatherActivity.iconIV.setImageResource(R.drawable.thunderstorm);
                }
                else if(id >= 300 && id < 500){
                    WeatherActivity.iconIV.setImageResource(R.drawable.lightrain);
                }
                else if(id >= 500 && id < 600){
                    WeatherActivity.iconIV.setImageResource(R.drawable.rain);
                }
                else if(id >= 600 && id <= 700){
                    WeatherActivity.iconIV.setImageResource(R.drawable.snow);
                }
                else if(id >= 701 && id <= 771){
                    WeatherActivity.iconIV.setImageResource(R.drawable.overcast);
                }
                else if(id >= 772 && id <= 800){
                    WeatherActivity.iconIV.setImageResource(R.drawable.overcast);
                }
                else if(id == 800){
                    WeatherActivity.iconIV.setImageResource(R.drawable.sunny);
                }
                else if(id >= 801 && id <= 804){
                    WeatherActivity.iconIV.setImageResource(R.drawable.cloudysun);
                }
                else if(id >= 900 && id <= 902){
                    WeatherActivity.iconIV.setImageResource(R.drawable.sunnythunderstorm);
                }
                else if(id == 903){
                    WeatherActivity.iconIV.setImageResource(R.drawable.snow);
                }
                else if(id == 904){
                    WeatherActivity.iconIV.setImageResource(R.drawable.sunny);
                }
                else if(id >= 905 && id <= 1000){
                    WeatherActivity.iconIV.setImageResource(R.drawable.sunnythunderstorm);
                }
                else{
                    WeatherActivity.iconIV.setImageResource(R.drawable.dunno);
                }

            } catch (Exception e) {
                Toast.makeText(WeatherActivity.context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(WeatherActivity.context, "Please enter a correct city name!", Toast.LENGTH_SHORT).show();
        }
    }
}
