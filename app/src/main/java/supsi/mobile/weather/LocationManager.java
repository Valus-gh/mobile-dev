package supsi.mobile.weather;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.List;

import io.nlopez.smartlocation.OnGeocodingListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class LocationManager {

    private static LocationManager instance;
    private Context context;

    public static LocationManager getInstance() {

        if (instance == null)
            instance = new LocationManager();

        return instance;

    }

    public void startListening(Context context) {

        this.context = context;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(LocationAccuracy.HIGH)
                .setDistance(0)
                .setInterval(5000);

        SmartLocation.with(context).location().continuous().config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Log.i("GPS", "Location" + location);
                    }
                });

    }

//    public Location getGeocodedLocation(String location){
//
//        final Location[] toReturn = new Location[1];
//
//        SmartLocation.with(context).geocoding()
//                .direct(location, new OnGeocodingListener() {
//                    @Override
//                    public void onLocationResolved(String s, List<LocationAddress> list) {
//                        if(list.size() > 0)
//                             toReturn[0] = list.get(0).getLocation();
//
//                    }
//                });
//
//        Log.i("GPS", toReturn[0].toString());
//
//        return toReturn[0];
//
//    }



}
