package supsi.mobile.weather.model;

public class WeatherRecord {

    private final String name;
    private float currentTemp;
    private float maxRecordedTemp;
    private float minRecordedTemp;

    public WeatherRecord(String name, float currentTemp, float maxRecordedTemp, float minRecordedTemp) {
        this.name = name;
        this.maxRecordedTemp = maxRecordedTemp;
        this.minRecordedTemp = minRecordedTemp;
        this.currentTemp = currentTemp;
    }

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
}
