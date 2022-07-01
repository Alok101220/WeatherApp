package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.weather.Retrofit.ApiClient;
import com.example.weather.Retrofit.ApiInterface;
import com.example.weather.Retrofit.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView updated_at, citytext, temp, status, temp_min, temp_max, sunrise, sunset, wind, pressure, humidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.address);
        updated_at=findViewById(R.id.updated_at);
        citytext=findViewById(R.id.citytext);
        temp=findViewById(R.id.temp);
        temp_min=findViewById(R.id.temp_min);
        temp_max=findViewById(R.id.temp_max);
        status=findViewById(R.id.status);
        wind=findViewById(R.id.wind);
        pressure=findViewById(R.id.pressure);
        humidity=findViewById(R.id.humidity);
        ImageView searchView=findViewById(R.id.search);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData(editText.getText().toString().trim());
            }
        });

    }

    private void getWeatherData(String name){
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Example> call=apiInterface.getWeatherData(name);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try{
                   citytext.setText(editText.getText().toString());
                   temp.setText(response.body().getMain().getTemp()+ " C");
                   temp_min.setText("Min temp : "+response.body().getMain().getTemp_min());
                   temp_max.setText("Max temp : "+response.body().getMain().getTemp_max());
                   pressure.setText(response.body().getMain().getPressure());
                   humidity.setText(response.body().getMain().getHumidity());
                   wind.setText(response.body().getWind().getSpeed());
//                   status.setText(response.body().getWeather().getMain());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
//            @Override
//            public void onBackPressed(){
//                super.onBackPressed();
//            }
        });

    }

}