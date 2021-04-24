package supsi.mobile.weather;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.openweathermap.api.model.currentweather.CurrentWeather;

import supsi.mobile.weather.model.WeatherRecord;
import supsi.mobile.weather.request.ResultProcessor;

public class NotifyTemperatureWorker extends Worker implements ResultProcessor<CurrentWeather> {
    private final Context context;

    public NotifyTemperatureWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        notifyCurrentLocationWeather();
        return Result.success();
    }

    public void notifyCurrentLocationWeather() {
        CoordsWeatherRecordFetcher fetcher = new CoordsWeatherRecordFetcher(this);
        Location location = LocationManager.getInstance().getLocation();
        fetcher.execute(
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude()));
    }

    @Override
    public void processResult(CurrentWeather currentWeather) {
        WeatherRecord toNotify = new WeatherRecord(
                currentWeather.getCityName(),
                (float) currentWeather.getMainParameters().getTemperature(),
                (float) currentWeather.getMainParameters().getMaximumTemperature(),
                (float) currentWeather.getMainParameters().getMinimumTemperature()
        );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(toNotify.getName())
                .setContentText("Current temperature " + toNotify.getCurrentTemp() + " celsius")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0, mBuilder.build());
    }
}
