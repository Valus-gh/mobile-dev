package supsi.mobile.weather.persistence;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import supsi.mobile.weather.model.WeatherRecord;

public class WeatherRecordService {

    private static List<WeatherRecord> localRecords = new CopyOnWriteArrayList<>();

    public static List<WeatherRecord> updateRecords(Context context) {

        new Thread(() -> {

            localRecords = RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .getWeatherRecords();

        }).start();

        return localRecords;

    }

    public static List<WeatherRecord> updateRecords(Context context, Runnable runnable) {

        new Thread(() -> {

            localRecords = RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .getWeatherRecords();

            new Handler(Looper.getMainLooper()).post(runnable);

        }).start();

        return localRecords;

    }

    public static List<WeatherRecord> getRecords() {

        return localRecords;

    }

    public static List<WeatherRecord> getRecords(Context context, Runnable runnable) {

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).post(runnable);

        }).start();

        return localRecords;

    }

    public static WeatherRecord getRecord(long index) {

        Optional<WeatherRecord> optional =
                localRecords.stream()
                        .filter((r) -> r.getId() == index)
                        .findFirst();

        if (optional.isPresent())
            return optional.get();
        else
            Log.d("TRACE", "record index not present on database");

        return null;

    }

    public static void addRecord(Context context, WeatherRecord record) {

        if (localRecords.stream().noneMatch((r -> record.getName().equals(r.getName())))) {

            localRecords.add(record);

            new Thread(() -> {

                RecordDatabase.getInstance(context)
                        .weatherRecordDao()
                        .insertRecord(record);

            }).start();

        }
    }

    public static void deleteRecords(Context context) {

        new Thread(() -> {

            RecordDatabase.getInstance(context)
                    .weatherRecordDao()
                    .deleteAll();

        }).start();

        localRecords.clear();

    }

}
