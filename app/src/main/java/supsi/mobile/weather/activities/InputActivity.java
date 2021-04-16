package supsi.mobile.weather.activities;

import androidx.appcompat.app.AppCompatActivity;

import supsi.mobile.weather.LocationManager;
import supsi.mobile.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    private EditText editText;
    private Button submit;
    private Button currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editText = findViewById(R.id.editText);
        submit = findViewById(R.id.submitButton);
        currentPos = findViewById(R.id.currentPosButton);

        submit.setOnClickListener((view) -> {
            if(editText.getText().length() != 0) {
                Intent intent = new Intent();
                intent.putExtra("type", 0);
                intent.putExtra("result", editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        currentPos.setOnClickListener((view) -> {

            Intent intent = new Intent();
            intent.putExtra("type", 1);
            String[] latlon = new String[2];
            latlon[0] = LocationManager.getInstance().getLocation().getLatitude() + "";
            latlon[1] = LocationManager.getInstance().getLocation().getLongitude() + "";
            intent.putExtra("result", latlon);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InputActivity.class);

        return intent;
    }
}