package net.mreunionlabs.permissionbug.ui.vm

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.mreunionlabs.permissionbug.util.CurrentLocationListenerKt

class BwMapFragmentViewModel(val currentLocationListener: CurrentLocationListenerKt) :
    ViewModel(), CoroutineScope {

    private var job = Job()
    override val coroutineContext = Dispatchers.Main + job

    var covered: Boolean = false
    //    var cwnMap: Map<String, CorridorWithNodes>? = null
//    var markerMap: HashMap<String, Marker>? = null
    var selectedMarker: Marker? = null
    //    var autoCompleteData = mutableListOf<String>()
//
    var gMap: GoogleMap? = null
//    val location: MutableLiveData<Location> = MutableLiveData()
//    var currentLocation: Location? = LocationHelper.fromLatLng(KomuttaLocationUtil.JAKARTA)
//
//    private var zoomed: Boolean = false
//    private var zoom = Constant.ZOOM_1

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

//    fun obsAllRoute(): LiveData<Map<String, CorridorWithNodes>> {
//
//        val liveData = MutableLiveData<Map<String, CorridorWithNodes>>()
//
//        launch {
//             liveData.value = corridorRepository.getAllRoute()
//        }
//
//        return liveData
//    }
//
//    fun getZoom(): Int {
//        zoom = if (zoomed) {
//            Constant.ZOOM_1
//        } else {
//            Constant.ZOOM_2
//        }
//
//        zoomed = !zoomed
//
//        return zoom
//    }

}