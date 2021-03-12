package supsi.mobile.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class RecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        //TODO use model to fill textviews when building fragment
        //fragment manager ...


    }

    public static Intent newIntent(Context packageContext, int extraParam){

        Intent intent = new Intent(packageContext, RecordDetailActivity.class);
        return intent;

    }


}