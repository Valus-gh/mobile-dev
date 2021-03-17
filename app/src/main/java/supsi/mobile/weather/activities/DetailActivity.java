package supsi.mobile.weather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import supsi.mobile.weather.fragments.DetailFragment;
import supsi.mobile.weather.R;
import supsi.mobile.weather.model.WeatherRecord;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int recordIndex = getIntent().getIntExtra("record", -1);

        Log.d("TRACE", recordIndex +"");

        if(recordIndex == -1){
            Log.d("TRACE", recordIndex +"");
        }else{
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.detailActivity);

            if(fragment == null){
                fragment = new DetailFragment(new WeatherRecord("Location 1", 10.f, 20.0f));
                fm.beginTransaction()
                        .add(R.id.detailActivity, fragment)
                        .commit();
            }


        }

    }

    public static Intent newIntent(Context packageContext, int recordIndex){
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra("record", recordIndex);
        return intent;
    }

}