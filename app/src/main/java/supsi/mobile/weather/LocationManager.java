package supsi.mobile.weather;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class LocationManager {

    private static LocationManager instance;
    private Location currentLocation;

    public static LocationManager getInstance() {

        if (instance == null)
            instance = new LocationManager();

        return instance;

    }

    public void startListening(Context context) {

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(LocationAccuracy.HIGH)
                .setDistance(0)
                .setInterval(5000);

        SmartLocation.with(context)
                .location()
                .continuous()
                .config(builder.build())
                .start(location -> {
                    Log.i("GPS", "Location" + location);
                    currentLocation = location;
                });

    }

    public Location getLocation() {
        return currentLocation;
    }

}
