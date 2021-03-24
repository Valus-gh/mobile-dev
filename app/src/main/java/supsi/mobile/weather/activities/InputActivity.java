package supsi.mobile.weather.activities;

import androidx.appcompat.app.AppCompatActivity;

import supsi.mobile.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    private EditText editText;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editText = findViewById(R.id.editText);
        submit = findViewById(R.id.submitButton);

        submit.setOnClickListener((view) -> {
            if(editText.getText().length() != 0) {
                Intent intent = new Intent();
                intent.putExtra("result", editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InputActivity.class);

        return intent;
    }
}