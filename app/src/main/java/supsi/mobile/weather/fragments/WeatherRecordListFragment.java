package supsi.mobile.weather.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import supsi.mobile.weather.R;
import supsi.mobile.weather.activities.DetailActivity;
import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.persistence.WeatherRecordService;

public class WeatherRecordListFragment extends Fragment {

    private List<WeatherRecord> records;
    private Context mainActivity;
    private RecyclerView recyclerView;

    public WeatherRecordListFragment(List<WeatherRecord> records, Context activity) {
        this.records = records;
        this.mainActivity = activity;
    }

    public WeatherRecordListFragment() {
        // Required empty public constructor
    }

    public static WeatherRecordListFragment newInstance() {
        WeatherRecordListFragment fragment = new WeatherRecordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weather_record_list, container, false);
        this.recyclerView = view.findViewById(R.id.weather_record_list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        WeatherRecordAdapter adapter = new WeatherRecordAdapter(records, mainActivity);
        this.recyclerView.setAdapter(adapter);

        return view;
    }

    public void reloadPlaces(){

        ((WeatherRecordAdapter) Objects.requireNonNull(recyclerView.getAdapter())).setRecords(WeatherRecordService.getRecords());
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

    }

    static class WeatherRecordHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView temperature;
        private WeatherRecord record;
        private Context mainActivity;

        public WeatherRecordHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.weather_record_list_item, viewGroup, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailActivity.newIntent(mainActivity, record.getId());
                    mainActivity.startActivity(intent);
                }
            });
            title = itemView.findViewById(R.id.weather_record_list_item);
            temperature = itemView.findViewById(R.id.weather_record_list_item_temperature);

        }

        public void bind(WeatherRecord entry, Context mainActivity) {
            Log.d("TRACE", entry.getId() +"");
            this.title.setText(entry.getName());
            temperature.setText(entry.getCurrentTemp() + " °C");
            this.record = entry;
            this.mainActivity = mainActivity;
        }

    }

    class WeatherRecordAdapter extends RecyclerView.Adapter<WeatherRecordHolder> {

        private final Context mainActivity;
        private List<WeatherRecord> records;

        public WeatherRecordAdapter(List<WeatherRecord> records, Context mainActivity) {
            this.records = records;
            this.mainActivity = mainActivity;
        }

        @NonNull
        @Override
        public WeatherRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WeatherRecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherRecordHolder holder, int position) {
            holder.bind(records.get(position), mainActivity);
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        public void setRecords(List<WeatherRecord> records){
            this.records = records;
        }

    }

}