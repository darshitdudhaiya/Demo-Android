import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class MyLocationListener : LocationListener {
    override fun onLocationChanged(location: Location) {
        // Handle location updates here
        val latitude = location.latitude
        val longitude = location.longitude

        // Update the map or perform other actions based on the new location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Handle changes in the status of the location provider (e.g., GPS status changes)
    }

    override fun onProviderEnabled(provider: String) {
        // Handle when the location provider (e.g., GPS) is enabled
    }

    override fun onProviderDisabled(provider: String) {
        // Handle when the location provider (e.g., GPS) is disabled
    }
}
