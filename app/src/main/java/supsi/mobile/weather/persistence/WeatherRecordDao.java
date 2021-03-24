package supsi.mobile.weather.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import supsi.mobile.weather.model.WeatherRecord;

@Dao
public interface WeatherRecordDao {

    @Query("SELECT * FROM records")
    List<WeatherRecord> getWeatherRecords();

    @Query("SELECT * FROM records where name = :name")
    WeatherRecord getRecord(String name);

    @Query("SELECT * FROM records where id = :id")
    WeatherRecord getRecord(int id);

    @Query("DELETE FROM records")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecord(WeatherRecord record);

    @Delete
    void deleteRecord(WeatherRecord record);

    @Update
    void updateRecord(WeatherRecord record);

}
