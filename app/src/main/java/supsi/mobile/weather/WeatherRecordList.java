package supsi.mobile.weather;

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

import supsi.mobile.weather.model.WeatherRecord;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherRecordList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherRecordList extends Fragment {

    private RecyclerView recyclerView;
    private WeatherRecordAdapter adapter;
    private List<WeatherRecord> entries = new ArrayList<>();

    public WeatherRecordList(List<WeatherRecord> entries) {
        this.entries = entries;
    }

    public WeatherRecordList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherRecordList.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherRecordList newInstance(String param1, String param2) {
        WeatherRecordList fragment = new WeatherRecordList();
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
        recyclerView = view.findViewById(R.id.weather_record_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new WeatherRecordAdapter(entries);
        recyclerView.setAdapter(adapter);

        return view;
    }

    static class WeatherRecordHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public WeatherRecordHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.weather_record_list_item, viewGroup, false));

            title = itemView.findViewById(R.id.weather_record_list_item);
        }

        public void bind(WeatherRecord entry) {
            title.setText(entry.getName());
        }

    }

    class WeatherRecordAdapter extends RecyclerView.Adapter<WeatherRecordHolder> {

        private final List<WeatherRecord> entries;

        public WeatherRecordAdapter(List<WeatherRecord> entries) {
            this.entries = entries;
        }

        @NonNull
        @Override
        public WeatherRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WeatherRecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherRecordHolder holder, int position) {
            holder.bind(entries.get(position));
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }
    }


}