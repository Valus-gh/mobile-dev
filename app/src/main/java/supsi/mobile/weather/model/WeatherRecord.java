package supsi.mobile.weather.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "records")
public class WeatherRecord {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "current_temp")
    private float currentTemp;

    @ColumnInfo(name = "max_temp")
    private float maxRecordedTemp;

    @ColumnInfo(name = "min_temp")
    private float minRecordedTemp;

    public WeatherRecord(long id, String name, float currentTemp, float maxRecordedTemp, float minRecordedTemp) {
        this.id = id;
        this.name = name;
        this.currentTemp = currentTemp;
        this.maxRecordedTemp = maxRecordedTemp;
        this.minRecordedTemp = minRecordedTemp;
    }

    @Ignore
    public WeatherRecord(String name, float currentTemp, float maxRecordedTemp, float minRecordedTemp) {
        this.name = name;
        this.maxRecordedTemp = maxRecordedTemp;
        this.minRecordedTemp = minRecordedTemp;
        this.currentTemp = currentTemp;
    }

    @Ignore
    public WeatherRecord(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getMaxRecordedTemp() {
        return maxRecordedTemp;
    }

    public void setMaxRecordedTemp(float maxRecordedTemp) {
        this.maxRecordedTemp = maxRecordedTemp;
    }

    public float getMinRecordedTemp() {
        return minRecordedTemp;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setMinRecordedTemp(float minRecordedTemp) {
        this.minRecordedTemp = minRecordedTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
