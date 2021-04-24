package supsi.mobile.weather.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.openweathermap.api.model.currentweather.CurrentWeather;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import supsi.mobile.weather.CoordsWeatherRecordFetcher;
import supsi.mobile.weather.LocationManager;
import supsi.mobile.weather.NotifyTemperatureWorker;
import supsi.mobile.weather.R;
import supsi.mobile.weather.fragments.WeatherRecordListFragment;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.WeatherRecordService;
import supsi.mobile.weather.request.ResultProcessor;
import supsi.mobile.weather.request.WeatherRecordFetcher;


public class MainActivity extends AppCompatActivity implements ResultProcessor<CurrentWeather> {

    private WeatherRecordListFragment fragment;
    private static final int requestCode = 24;

    @Override
    protected void onActivityResult(int reqCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == requestCode && resultCode == RESULT_OK) {

            if(Objects.requireNonNull(data).getIntExtra("type", -1) == 0){

                String city = data.getStringExtra("result");
                Log.d("TRACE", "result " + city);
                WeatherRecordFetcher fetcher = new WeatherRecordFetcher(MainActivity.this);
                fetcher.execute(city);

            }else if(data.getIntExtra("type", -1) == 1){

                String[] lonlat = data.getStringArrayExtra("result");
                CoordsWeatherRecordFetcher fetcher = new CoordsWeatherRecordFetcher(MainActivity.this);
                fetcher.execute(lonlat[0], lonlat[1]);

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("GPS PERMISSION", "Permission not granted");
            requestPermissions();
        } else {
            Log.i("GPS PERMISSION", "Permission granted");
            LocationManager.getInstance().startListening(this);
        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        PeriodicWorkRequest periodicRequest = new PeriodicWorkRequest.Builder(NotifyTemperatureWorker.class, 15, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("POLL WORK", ExistingPeriodicWorkPolicy.KEEP, periodicRequest);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            Intent intent = InputActivity.newIntent(getApplicationContext());
            startActivityForResult(intent, requestCode);
        });

        //WeatherRecordService.deleteRecords(this);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (WeatherRecordListFragment) fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new WeatherRecordListFragment(WeatherRecordService.updateRecords(this, () -> fragment.reloadPlaces()), MainActivity.this);

            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            LocationManager.getInstance().startListening(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                LocationManager.getInstance().startListening(this);
        }
    }

    @Override
    public void processResult(CurrentWeather currentWeather) {

        WeatherRecordService.addRecord(this,
                new WeatherRecord(
                        currentWeather.getCityName(),
                        (float) currentWeather.getMainParameters().getTemperature(),
                        (float) currentWeather.getMainParameters().getMaximumTemperature(),
                        (float) currentWeather.getMainParameters().getMinimumTemperature()
                ));


        fragment.reloadPlaces();

    }

}