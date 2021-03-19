package supsi.mobile.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import supsi.mobile.weather.R;
import supsi.mobile.weather.model.WeatherRecord;

public class DetailFragment extends Fragment {

    private WeatherRecord record;

    public DetailFragment(WeatherRecord record) {
        this.record = record;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView location = view.findViewById(R.id.titleLabel);
        TextView currentTemperature = view.findViewById(R.id.temperatureTextview);
        TextView maxTemperature = view.findViewById(R.id.maxTemperatureTextview);
        TextView minTemperature = view.findViewById(R.id.minTemperatureTextview);

        if(record != null)
        {
            location.setText(record.getName());
            currentTemperature.setText(String.format("%s C", record.getCurrentTemp()));
            maxTemperature.setText(String.format("%s C", record.getMaxRecordedTemp()));
            minTemperature.setText(String.format("%s C", record.getMinRecordedTemp()));

        }


        return view;
    }
}