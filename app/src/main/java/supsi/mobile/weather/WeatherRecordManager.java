package supsi.mobile.weather;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONException;
import java.io.IOException;

import supsi.mobile.weather.model.WeatherRecord;

public class WeatherRecordManager {

    private final OpenWeatherMap owm;
    private static WeatherRecordManager instance;

    public WeatherRecordManager() {
        owm = new OpenWeatherMap("2ce39100e5b965b227777e0715587cff");
        owm.setUnits(OpenWeatherMap.Units.METRIC);
    }

    WeatherRecordManager getInstance() {
        if (instance == null)
            instance = new WeatherRecordManager();

        return instance;

    }

    public WeatherRecord getWeatherRecordByCityName(String cityName) throws IOException, JSONException {
        CurrentWeather currentWeather = owm.currentWeatherByCityName(cityName);

        if (currentWeather.hasResponseCode()
                && currentWeather.getResponseCode() == 200
                && currentWeather.hasCityName())
            return new WeatherRecord(
                    currentWeather.getCityName(),
                    currentWeather.getMainInstance().getTemperature(),
                    currentWeather.getMainInstance().getMaxTemperature(),
                    currentWeather.getMainInstance().getMinTemperature());

        return null;
    }
}
