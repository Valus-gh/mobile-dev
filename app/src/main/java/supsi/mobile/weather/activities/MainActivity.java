package supsi.mobile.weather.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;

import supsi.mobile.weather.R;
import supsi.mobile.weather.WeatherRecordManager;
import supsi.mobile.weather.fragments.WeatherRecordListFragment;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.WeatherRecordService;


public class MainActivity extends AppCompatActivity {

    private WeatherRecordListFragment fragment;
    private FloatingActionButton fab;
    private static final int requestCode = 24;

    @Override
    protected void onActivityResult(int reqCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == requestCode) {
            if (resultCode == RESULT_OK) {
                String city = data.getStringExtra("result");
                try {
                    WeatherRecord record = WeatherRecordManager.getInstance().getWeatherRecordByCityName(city);
                    WeatherRecordService.addRecord(this, record);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.d("TRACE", "error eededede");
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
            Intent intent = InputActivity.newIntent(this);
            startActivityForResult(intent, requestCode);
        });

        //*********************//

        //LocationManager.getInstance().startListening(getApplicationContext());

        //*********************//

        WeatherRecordService.deleteRecords(this);

        WeatherRecordService.getRecords(this);

        //fillInitialState(this);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (WeatherRecordListFragment) fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new WeatherRecordListFragment(WeatherRecordService.getRecords(this), MainActivity.this);

            fillInitialState();

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

}