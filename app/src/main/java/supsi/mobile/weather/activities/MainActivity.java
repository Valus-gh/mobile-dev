package supsi.mobile.weather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import supsi.mobile.weather.R;
import supsi.mobile.weather.fragments.WeatherRecordListFragment;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.RecordDatabase;
import supsi.mobile.weather.persistence.WeatherRecordService;


public class MainActivity extends AppCompatActivity {

    private WeatherRecordListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********************//

        //LocationManager.getInstance().startListening(getApplicationContext());

        //*********************//

        WeatherRecordService.deleteRecords(this);

        WeatherRecordService.getRecords(this);

        //fillInitialState(this);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (WeatherRecordListFragment)fm.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new WeatherRecordListFragment(WeatherRecordService.getRecords(this), MainActivity.this);

            fillInitialState(this);

            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

    }

    private void fillInitialState(Context context){

        WeatherRecordService.addRecord(this, new WeatherRecord(0,"1", 10.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(1,"2", 11.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(2,"3", 12.f, 20.f, 10.f));
        WeatherRecordService.addRecord(this, new WeatherRecord(3,"4", 13.f, 20.f, 10.f));

    }

}