package supsi.mobile.weather.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.openweathermap.api.model.currentweather.CurrentWeather;

import supsi.mobile.weather.R;
import supsi.mobile.weather.fragments.WeatherRecordListFragment;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.WeatherRecordService;
import supsi.mobile.weather.request.ResultProcessor;
import supsi.mobile.weather.request.WeatherRecordFetcher;


public class MainActivity extends AppCompatActivity implements ResultProcessor<CurrentWeather> {

    private WeatherRecordListFragment fragment;
    private FloatingActionButton fab;
    private static final int requestCode = 24;

    @Override
    protected void onActivityResult(int reqCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == requestCode && resultCode == RESULT_OK) {

            String city = data.getStringExtra("result");
            Log.d("TRACE", "result " + city);

            WeatherRecordFetcher fetcher = new WeatherRecordFetcher(MainActivity.this);
            fetcher.execute(city);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
            Intent intent = InputActivity.newIntent(getApplicationContext());
            startActivityForResult(intent, requestCode);
        });

        //*********************//

        //LocationManager.getInstance().startListening(getApplicationContext());

        //*********************//

        //WeatherRecordService.deleteRecords(this);

       // fillInitialState();

        FragmentManager fm = getSupportFragmentManager();
        fragment = (WeatherRecordListFragment) fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new WeatherRecordListFragment(WeatherRecordService.getRecords(this, () -> fragment.reloadPlaces()), MainActivity.this);

            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

    }

    private void fillInitialState() {
        WeatherRecordService.addRecord(this, new WeatherRecord(0, "1", 10.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(1, "2", 11.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(2, "3", 12.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(3, "4", 13.f, 20.f, 10.f));
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

//        finish();
//        startActivity(getIntent());

    }
}