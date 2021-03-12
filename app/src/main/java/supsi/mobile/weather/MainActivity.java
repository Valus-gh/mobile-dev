package supsi.mobile.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import supsi.mobile.weather.model.WeatherRecord;

//TODO complete layout for detail fragment

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<WeatherRecord> entries = new ArrayList<>();

        LocationManager.getInstance().startListening(getApplicationContext());

        for(int i = 0; i < 100; i++)
            entries.add(new WeatherRecord("Test"));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.list_fragment_container);

        if(fragment == null){
            fragment = new WeatherRecordList(entries);
            fm.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

    }
}