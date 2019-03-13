package net.mreunionlabs.permissionbug.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by abangkis on 12/09/2017.
 */

public class KomuttaLocationUtil {

    private static final String TAG = "YCityLocationUtil";

    public static final LatLng JAKARTA = new LatLng(-6.313051485723744, 106.82606644928455);
    public static final LatLngBounds JAKARTA_BOUNDS = new LatLngBounds(new LatLng(-6.566445935938531, 106.57117679715157), new LatLng(-6.049718526248074, 107.13022258132696));

    public static final LatLng PALEMBANG = new LatLng(-2.976973, 104.774766);
    public static final LatLngBounds PALEMBANG_BOUNDS = new LatLngBounds(new LatLng(-3.034851, 104.861153), new LatLng(-2.872333, 104.676102));
    private static final int TWO_MINUTES = 120000;


    private static LatLngBounds calculateAllMarkersBound(Collection<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Iterator var2 = markers.iterator();

        while (var2.hasNext()) {
            Marker marker = (Marker) var2.next();
            builder.include(marker.getPosition());
        }

        return builder.build();
    }
}
