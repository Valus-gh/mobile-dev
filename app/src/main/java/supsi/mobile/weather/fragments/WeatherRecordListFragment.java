package supsi.mobile.weather.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import supsi.mobile.weather.R;
import supsi.mobile.weather.activities.DetailActivity;
import supsi.mobile.weather.model.WeatherRecord;

public class WeatherRecordListFragment extends Fragment {

    private List<WeatherRecord> entries = new ArrayList<>();
    private Context mainActivity;

    public WeatherRecordListFragment(List<WeatherRecord> entries, Context activity) {
        this.entries = entries;
        this.mainActivity = activity;
    }

    public WeatherRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
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
        RecyclerView recyclerView = view.findViewById(R.id.weather_record_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        WeatherRecordAdapter adapter = new WeatherRecordAdapter(entries, mainActivity);
        recyclerView.setAdapter(adapter);

        return view;
    }

    static class WeatherRecordHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private int index;
        private Context mainActivity;

        public WeatherRecordHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.weather_record_list_item, viewGroup, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailActivity.newIntent(mainActivity, index);
                    mainActivity.startActivity(intent);
                }
            });
            title = itemView.findViewById(R.id.weather_record_list_item);


        }

        public void bind(WeatherRecord entry, int index, Context mainActivity) {
            this.title.setText(entry.getName());
            this.index = index;
            this.mainActivity = mainActivity;
        }

    }

    class WeatherRecordAdapter extends RecyclerView.Adapter<WeatherRecordHolder> {

        private final List<WeatherRecord> entries;
        private final Context mainActivity;

        public WeatherRecordAdapter(List<WeatherRecord> entries, Context mainActivity) {
            this.entries = entries;
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
            holder.bind(entries.get(position), position, mainActivity);
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }
    }


}