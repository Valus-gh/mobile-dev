package supsi.mobile.weather.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import supsi.mobile.weather.model.WeatherRecord;

@Database(entities = WeatherRecord.class, version = 1, exportSchema = false)
public abstract class RecordDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "records_db";
    private static RecordDatabase instance;

    public static RecordDatabase getInstance(Context context) {

        if (instance == null)
            instance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            RecordDatabase.class,
                            RecordDatabase.DATABASE_NAME)
                    .build();

        return instance;

    }

    public abstract WeatherRecordDao weatherRecordDao();

}
