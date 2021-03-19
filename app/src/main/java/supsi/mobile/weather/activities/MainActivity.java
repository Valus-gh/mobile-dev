package supsi.mobile.weather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import supsi.mobile.weather.R;
import supsi.mobile.weather.fragments.WeatherRecordListFragment;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.RecordDatabase;

//TODO complete layout for detail fragment

public class MainActivity extends AppCompatActivity {

    private List<WeatherRecord> entries = new ArrayList<>();
    private WeatherRecordListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********************//

        //LocationManager.getInstance().startListening(getApplicationContext());

        //*********************//

        FragmentManager fm = getSupportFragmentManager();
        fragment = (WeatherRecordListFragment)fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new WeatherRecordListFragment(entries, MainActivity.this);

            dummyRecords(this);

            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

    }

    private void dummyRecords(Context context){

        new Thread(() -> {

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .deleteAll();

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .insertRecord(new WeatherRecord("1", 10.f, 20.f, 10.f));

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .insertRecord(new WeatherRecord("2", 10.f, 16.f, 10.f));

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .insertRecord(new WeatherRecord("3", 10.f, 18.f, 10.f));

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .insertRecord(new WeatherRecord("4", 10.f, 19.f, 10.f));


            entries = RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .getWeatherRecords();

            fragment.refreshUI(context);

        }).start();

    }

}