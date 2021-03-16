package supsi.mobile.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import supsi.mobile.weather.model.WeatherRecord;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int recordIndex = getIntent().getIntExtra("record", -1);

        if(recordIndex != -1){
            Log.d("TRACE", recordIndex +"");
        }

    }

    public static Intent newIntent(Context packageContext, int recordIndex){
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra("record", recordIndex);
        return intent;
    }

}