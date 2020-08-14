package com.muradismayilov.howstheweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.rengwuxian.materialedittext.MaterialEditText;

public class CityActivity extends AppCompatActivity {

    private ImageView backgroundIV;
    private MaterialEditText cityNameET;
    private Button searchBTN;

    private AdView adView;

    TextView privacy_policyTV;

    InterstitialAd interstitialAd;
    AdRequest adRequest2;

    TextView supportTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        backgroundIV = findViewById(R.id.backgroundIV);
        Glide.with(this).load(R.drawable.city_background).into(backgroundIV);

        MobileAds.initialize(this,"ca-app-pub-3531666375863646/4311647892");
        adView = findViewById(R.id.adView);
        AdRequest adRequest_1 = new AdRequest.Builder().build();
        adView.loadAd(adRequest_1);

        cityNameET = findViewById(R.id.cityNameET);
        searchBTN = findViewById(R.id.searchBTN);

        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city_name;
                if (cityNameET.getText().toString().matches("")) {
                    Toast.makeText(CityActivity.this, "Please enter a city name!", Toast.LENGTH_SHORT).show();
                } else {
                    city_name = cityNameET.getText().toString().trim();

                    Intent intent = new Intent(CityActivity.this, WeatherActivity.class);
                    intent.putExtra("city name", city_name);
                    startActivity(intent);
                    CityActivity.this.finish();
                }
            }
        });

        privacy_policyTV = findViewById(R.id.privacy_policyTV);
        privacy_policyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://weathercheckapp.blogspot.com/p/privacy-policy.html");
                Intent privacy_policy_intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(privacy_policy_intent);
            }
        });

        supportTV = findViewById(R.id.supportTV);
        supportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CityActivity.this);
                builder.setTitle("Support");
                builder.setMessage("If you want to support me, just click the ads or review on Google Play :)");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3531666375863646/6076209608");
        adRequest2 = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest2);

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();

                finish();
                CityActivity.this.finish();

                interstitialAd.loadAd(adRequest2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }
}