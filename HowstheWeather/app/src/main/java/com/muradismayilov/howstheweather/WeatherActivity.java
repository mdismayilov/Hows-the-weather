package com.muradismayilov.howstheweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class WeatherActivity extends AppCompatActivity {

    private ImageView backgroundIV;
    public static ImageView iconIV;
    public static TextView tempTV, mainTV, descriptionTV, nameTV;

    private String city_name;

    public static Context context;

    private Button backBTN;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        backgroundIV = findViewById(R.id.backgroundIV);
        Glide.with(this).load(R.drawable.weather_background).into(backgroundIV);

        MobileAds.initialize(this,"ca-app-pub-3531666375863646/4311647892");
        adView = findViewById(R.id.adView);
        AdRequest adRequest_1 = new AdRequest.Builder().build();
        adView.loadAd(adRequest_1);

        iconIV = findViewById(R.id.iconIV);
        tempTV = findViewById(R.id.tempTV);
        mainTV = findViewById(R.id.mainTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        nameTV = findViewById(R.id.nameTV);

        city_name = getIntent().getStringExtra("city name").replace(" ","%20");

        context = WeatherActivity.this;

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city_name +
                "&appid=0afe4b849a02440229c8ac5e00332b51";

        DownloadData downloadData = new DownloadData();
        downloadData.execute(url);

        backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, CityActivity.class);
                startActivity(intent);
                WeatherActivity.this.finish();
            }
        });
    }
}