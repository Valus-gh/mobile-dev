package supsi.mobile.weather.request;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

public class WeatherRecordFetcher extends AsyncTask<String, Void, CurrentWeather> {

    private static final String KEY = "2ce39100e5b965b227777e0715587cff";
    private final ResultProcessor<CurrentWeather> processor;

    public WeatherRecordFetcher(ResultProcessor<CurrentWeather> processor) {
        this.processor = processor;
    }

    private CurrentWeather getWeatherFromCity(String name) {
        DataWeatherClient data = new UrlConnectionDataWeatherClient(KEY);
        CurrentWeatherOneLocationQuery query = QueryBuilderPicker.pick()
                .currentWeather()
                .oneLocation()
                .byCityName(name)
                .type(Type.ACCURATE)
                .language(Language.ITALIAN)
                .responseFormat(ResponseFormat.JSON)
                .unitFormat(UnitFormat.METRIC)
                .build();

        CurrentWeather currentWeather = data.getCurrentWeather(query);

        Log.d("TRACE", prettyPrint(currentWeather));

        return currentWeather;
    }


    @Override
    protected CurrentWeather doInBackground(String... strings) {
        return getWeatherFromCity(strings[0]);
    }

    @Override
    protected void onPostExecute(CurrentWeather currentWeather) {
        super.onPostExecute(currentWeather);

        processor.processResult(currentWeather);
    }

    @SuppressLint("DefaultLocale")
    private static String prettyPrint(CurrentWeather currentWeather) {
        return String.format(
                "Current weather in %s(%s):\ntemperature: %.1f ℃\nhumidity: %.1f %%\npressure: %.1f hPa\n",
                currentWeather.getCityName(), currentWeather.getSystemParameters().getCountry(),
                currentWeather.getMainParameters().getTemperature(),
                currentWeather.getMainParameters().getHumidity(),
                currentWeather.getMainParameters().getPressure()
        );
    }

}
