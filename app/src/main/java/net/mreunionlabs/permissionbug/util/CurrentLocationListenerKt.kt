package net.mreunionlabs.permissionbug.util

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import timber.log.Timber

class CurrentLocationListenerKt constructor(appContext: Context) : LiveData<Location>(),
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var googleApiClient: GoogleApiClient? = null

    init {
        Timber.tag("LocationListenerKt")
        buildGoogleApiClient(appContext)
    }

    @Synchronized
    private fun buildGoogleApiClient(appContext: Context) {
        Timber.d("Build google api client")
        googleApiClient = GoogleApiClient.Builder(appContext)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }

    override fun onActive() {
        googleApiClient!!.connect()
    }

    override fun onInactive() {
        Timber.d("onInactive: Disconnecting google API Client")
        if (googleApiClient!!.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
        googleApiClient!!.disconnect()
    }

    override fun onConnected(connectionHint: Bundle?) {
        Timber.d("connected to google api client")

//        val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
//
//        lastLocation?.let { value = it } ?: Timber.e("onConnected: last location value is NULL")
//
//        if (hasActiveObservers() && googleApiClient!!.isConnected) {
//            Timber.d("onConnected:Requesting location update ")
//            val locationRequest = LocationHelper.createRequest() // needs High Priority Accuracy to work in emulator
//            //            LocationRequest locationRequest = LocationRequest.create();
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
//        }
    }

    override fun onConnectionSuspended(cause: Int) {
        Timber.w("On Connection suspended $cause")
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Timber.e("GoogleApiClient connection has failed $result")
    }

    override fun onLocationChanged(location: Location) {
        Timber.d("Location changed received: $location")
        value = location
    }

//    companion object {
//
//        private val TAG = "CurrentLocationListener"
//        private var instance: CurrentLocationListener? = null
//
//        fun getInstance(appContext: Context): CurrentLocationListener {
//            if (instance == null) {
//                instance = CurrentLocationListener(appContext)
//            }
//
//            return instance
//        }
//    }
}