package supsi.mobile.weather.model;

public class WeatherRecord {

    private final String name;
    private float maxRecordedTemp;
    private float minRecordedTemp;

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

    public void setMinRecordedTemp(float minRecordedTemp) {
        this.minRecordedTemp = minRecordedTemp;
    }
}
