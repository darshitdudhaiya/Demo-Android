package com.example.demo


//import com.mapquest.directions.Directions
//import com.mapquest.directions.RouteOptions
//import com.mapquest.directions.RouteResponse
//import com.mapquest.directions.Session
//import com.mapquest.directions.io.DirectionsService
//import com.mapquest.directions.io.DirectionsServiceFactory

import MyLocationListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.webkit.WebView
import android.widget.Button
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.Manifest
import android.content.pm.ActivityInfo
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var webView: WebView
    var map: MapView? = null
    val locationListener = MyLocationListener()
    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        // Handle permissions first, before the map is created (not depicted here)

        // Load/initialize the osmdroid configuration
        val ctx: Context = applicationContext
//        Configuration.getInstance().load(ctx, getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

        // Setting this before the layout is inflated is a good idea
        // It should ensure that the map has a writable location for the map cache, even without permissions
        // If no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        // Note: The load method also sets the HTTP User Agent to your application's package name
        // Abusing osm's tile servers will get you banned based on this string

        // Inflate and create the map
//        setContentView(R.layout.activity_main)

        map = findViewById<MapView>(R.id.map)
        var marker = Marker(map)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        // Customize and add the marker to the map
        marker.icon = resources.getDrawable(R.drawable.baseline_pin_drop_24) // Customize the marker's appearance
        map!!.overlays.add(marker)
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))


//        marker.icon = resources.getDrawable(R.drawable.baseline_pin_drop_24) // Customize the marker's appearance
//        map!!.overlays.add(marker)

        if (map != null) {
            map!!.setTileSource(TileSourceFactory.MAPNIK)
            map!!.setBuiltInZoomControls(true);
            map!!.setMultiTouchControls(true);
            val mapController = map!!.controller
            mapController.setZoom(9.5)
            var startPoint = GeoPoint(22.470584279471584, 70.05539384548089)
            mapController.setCenter(startPoint)

            val currentLocationButton: Button = findViewById<Button>(R.id.currentLocationButton)
            currentLocationButton.setOnClickListener { checkLocationPermission () }
//


        } else {
            // Handle the case where 'map' is null, e.g., by showing an error message or taking appropriate action.
        }
    }

    override fun onResume() {
        super.onResume()
        // This will refresh the osmdroid configuration on resuming
        // If you make changes to the configuration, use
        // val prefs = getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        // Configuration.getInstance().load(this, prefs)
        map?.onResume() // Needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        // This will refresh the osmdroid configuration on pausing
        // If you make changes to the configuration, use
        // val prefs = getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        // Configuration.getInstance().save(this, prefs)
        map?.onPause() // Needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

        finish()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkLocationPermission() {

        val task: Task<Location> = fusedLocationClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(applicationContext, "${it.longitude} ${it.latitude}", Toast.LENGTH_LONG).show()
                val mapController = map!!.controller
                mapController.setZoom(20.1)
                var startPoint = GeoPoint(it.latitude, it.longitude)
                mapController.setCenter(startPoint)
                map!!.invalidate() // Refresh the map

                // Create a marker
                val marker = Marker(map)
                marker.position = startPoint
                marker.icon = resources.getDrawable(R.drawable.baseline_pin_drop_24) // Set the marker icon
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.isDraggable = true // Allow marker to be draggable

                // Add the marker to the map
                map!!.overlays.add(marker)
                map!!.invalidate() // Refresh the map to display the marker

                requestLocationUpdates()
            }
        }
    }

    private fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // Example: Use GPS as the location provider
                1000, // Minimum time interval (1 second)
                0.0f, // Minimum distance (0 meters)
                locationListener // Your custom LocationListener
            )
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
    }
}
