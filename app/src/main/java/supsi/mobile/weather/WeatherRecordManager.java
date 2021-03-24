package supsi.mobile.weather;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import org.json.JSONException;
import java.io.IOException;

import supsi.mobile.weather.model.WeatherRecord;

public class WeatherRecordManager {

    private final OWM owm;
    private static WeatherRecordManager instance;

    public WeatherRecordManager() {
        owm = new OWM("2ce39100e5b965b227777e0715587cff");
        owm.setUnit(OWM.Unit.METRIC);
    }

    public static WeatherRecordManager getInstance() {
        if (instance == null)
            instance = new WeatherRecordManager();

        return instance;

    }

    public WeatherRecord getWeatherRecordByCityName(String cityName) throws APIException {
        CurrentWeather currentWeather = owm.currentWeatherByCityName(cityName);

        if (currentWeather.hasRespCode()
                && currentWeather.getRespCode() == 200
                && currentWeather.hasCityName())
            return new WeatherRecord(
                    currentWeather.getCityName(),
                    currentWeather.getMainData().getTemp().floatValue(),
                    currentWeather.getMainData().getTempMax().floatValue(),
                    currentWeather.getMainData().getTempMin().floatValue());

        return null;
    }
}
