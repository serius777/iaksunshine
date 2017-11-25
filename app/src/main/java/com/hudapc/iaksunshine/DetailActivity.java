package com.hudapc.iaksunshine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hudapc.iaksunshine.databinding.DetailActivityBinding;
import com.hudapc.iaksunshine.model.ForeCast;
import com.hudapc.iaksunshine.model.Weather;

public class DetailActivity extends AppCompatActivity
{
    DetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity);
        //getSupportActionBar().setHomeButtonEnabled(true);

        Intent inIntent = getIntent();
        ForeCast item = new Gson().fromJson(inIntent.getStringExtra("json_forecast"), ForeCast.class);
        Weather weather = item.getWeather().get(0);
        binding.tvMainWaether.setText(weather.getMain());
        binding.tvMaxTemp.setText("" + item.getTemp().getMax());
        binding.tvMinTemp.setText("" + item.getTemp().getMin());
    }
}
