package net.mreunionlabs.permissionbug.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_bw_map.*
import net.mreunionlabs.permissionbug.R
import net.mreunionlabs.permissionbug.ui.vm.BwMapFragmentViewModel
import net.mreunionlabs.permissionbug.util.KomuttaLocationUtil
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit


class BwMapFragment3 : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
    GoogleMap.OnInfoWindowClickListener {
    companion object {
        const val TAG = "BwMapFragmentKt"

        fun newInstance(): BwMapFragment3 {
            return BwMapFragment3()
        }
    }

    private val vm: BwMapFragmentViewModel by viewModel()

    private var startTime: Long = 0

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.tag(BwMapFragment3::class.java.simpleName)
        startTime = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bw_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (childFragmentManager.findFragmentById(R.id.gMap) as SupportMapFragment?)?.getMapAsync(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.gMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        initSlidingUpPanel()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Context)
        // fixme uncomment getLastLocation to reproduce the bug
//        getLastLocation()
//        childFragmentManager.executePendingTransactions()

//        vm.location.observe(this, Observer {
//            Timber.d("onChanged: location updated $it")
//            vm.currentLocation = it
//            showRoute()
//        })
        // getCurrentLocationFromFusedClientAPI();
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() = runWithPermissions(Manifest.permission.ACCESS_COARSE_LOCATION) {
        val locationClient = fusedLocationClient
        locationClient?.lastLocation?.addOnSuccessListener {
            // Got last known location. In some rare situations this can be null.
            Timber.d("last location $it")
//            vm.currentLocation = it
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            startTime = System.currentTimeMillis()
        } else {
            logMapDuration()
        }
    }

    private fun logMapDuration() {
        if (startTime > 0) {
            val duration = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime)
            Timber.d("setUserVisibleHint: map duration: $duration")
            startTime = 0
        }
    }

    private fun initSlidingUpPanel() {
        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {

            }

            override fun onPanelStateChanged(
                panel: View,
                previousState: SlidingUpPanelLayout.PanelState,
                newState: SlidingUpPanelLayout.PanelState
            ) {
                if (SlidingUpPanelLayout.PanelState.ANCHORED == newState) {
                    vm.covered = true
                }
            }
        })

        sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        imgCamera?.drawable?.let {
            DrawableCompat.setTint(it, ContextCompat.getColor(context!!, R.color.primary_material_dark))
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        //        gMap.setMyLocationEnabled(true); fixme enable my location. Use fragment in regular map activity
        Timber.d("onMapReady")
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KomuttaLocationUtil.JAKARTA_BOUNDS.center, 1f))

        gMap.setOnMarkerClickListener(this)
        gMap.setOnMapClickListener(this)
        gMap.setOnInfoWindowClickListener(this)
        vm.gMap = gMap

        //        mType = getIntent().getIntExtra("type",-1);
        //        Timber.d("Map type: " + mType);

        showRoute()
    }

    //    @Override
    //    public void setUserVisibleHint(boolean isVisibleToUser) {
    //        super.setUserVisibleHint(isVisibleToUser);
    //
    //        Timber.d("setUserVisibleHint: visible " + isVisibleToUser + " gMap: " + gMap);
    //
    //        //TODO don't put too much stuff here
    //        //Fixme still have a problem when the tab is tapped instead of swipe
    //        if (isVisibleToUser) {
    //            if (gMap != null) {
    //                showBuswayMap(gMap);
    //            }
    //        }
    //    }

    private fun showRoute() {
        Timber.d("Show route")
//        btnFilter.visibility = View.VISIBLE

//        if (vm.gMap != null) {
//            vm.obsAllRoute().observe(this, Observer { map ->
//                Timber.d("Draw route")
//                vm.cwnMap = map
//
////                vm.markerMap = BWRouteDrawer.drawCorridors(vm.gMap, map, vm.currentLocation)
////
////                val allNodes = NodeManager.getAllCWNNodes(map)
////                for (rn in allNodes) {
////                    vm.autoCompleteData.add(rn.node.name)
////                }
//            })
//        }

    }

    override fun onDestroy() {
        logMapDuration()
        super.onDestroy()
    }


    override fun onMarkerClick(marker: Marker): Boolean {
        Timber.d("Marker clicked!")
        //        if(mShelters == null) // only show in ALL Busway mode
        //            mBtnFilter.setVisibility(View.VISIBLE);
        //        else
        //            mBtnFilter.setVisibility(View.GONE);

        marker.showInfoWindow()
        vm.selectedMarker = marker
        //        updateNearByUpPanel(marker);
        return true
    }


    override fun onMapClick(latLng: LatLng) {
        Timber.d("Map Clicked $latLng")
        if (vm.selectedMarker != null) {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            vm.selectedMarker = null
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        Timber.d("Info window clicked")
//        vm.gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, vm.getZoom().toFloat()))
    }


//    fun showFilter(activity: Activity) {
//        Timber.d("showFilter: ")
//        if (filterDialog == null) {
//            filterDialog = createFilterDialog(activity)
//        }
//
//        filterDialog!!.show()
//    }

//    private fun createFilterDialog(activity: Activity): AlertDialog {
//        Timber.d("createFilterDialog: ")
//        //
//        //        if (bwCorridorDBAdapter == null) {
//        //            Timber.d("createFilterDialog: initializing bw corridor db adapter");
//        //            bwCorridorDBAdapter = SingletonDB.getDB(activity).getBWCorridorDBAdapter();
//        //        }
//        //
//        //        List<Corridor> corridors = CorridorHelper.getPickerCorridors(bwCorridorDBAdapter);
//        //        shelterArray = new String[corridors.size()];
//        //        for (int i = 0; i < shelterArray.length; i++) {
//        //            shelterArray[i] = corridors.get(i).getName();
//        //        }
//        //
//        //        checkedItems = new boolean[shelterArray.length];
//        //        for (int i = 0; i < checkedItems.length; i++) {
//        //            checkedItems[i] = true;
//        //        }
//        //
//        val builder = AlertDialog.Builder(activity)
//        //        builder.setTitle("Filter corridor")
//        //                .setMultiChoiceItems(shelterArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//        //                    @Override
//        //                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//        //                        checkedItems[which] = isChecked;
//        //                    }
//        //                })
//        //                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//        //                    @Override
//        //                    public void onClick(DialogInterface dialog, int id) {
//        //                        showFilteredMap();
//        //                    }
//        //                })
//        //                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//        //                    @Override
//        //                    public void onClick(DialogInterface dialog, int id) {
//        //                        dialog.dismiss();
//        //                    }
//        //                });
//        //
//        return builder.create()
//    }
//
//    fun showFilteredMap() {
//        if (gMap == null) {
//            Timber.d("showFilteredMap: re-initilizing gmap") // fixme hacky-hacky stuff, why can't we access the original gMap?
//            initMap()
//            filterMap()
//        } else {
//            filterMap()
//        }
//
//    }
//
//    private fun filterMap() {
//        //        if (gMap != null) {
//        //            gMap.clear();
//        //
//        //            if (cwnMap == null)
//        //                cwnMap = CorridorHelper.getCorridorsWithNodes(bwCorridorDBAdapter, bwRouteDBAdapter);
//        //
//        //            List<String> selection = new ArrayList<String>();
//        //            for (int i = 0; i < checkedItems.length; i++) {
//        //                if (checkedItems[i]) {
//        //                    Timber.d("Adding: " + shelterArray[i]);
//        //                    selection.add(shelterArray[i]);
//        //                }
//        //            }
//        //
//        //            Location location = ViewModelProviders.of(this).get(BWMapFragmentViewModel.class).getCurrentLocation();
//        //
//        //            Map<String, CorridorWithNodes> filtered = CorridorHelper.filter(cwnMap, selection);
//        //            BWRouteDrawer.drawCorridors(gMap, filtered, location);
//        //        } else {
//        //            Log.e(TAG, "showFilteredMap: GMap is NULL when filtered!");
//        //        }
//    }


//    private fun getCurrentLocationFromFusedClientAPI() {
//
//        //        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getContext());
//        //        PendingIntent pendingIntent;
//        //        client.requestLocationUpdates(LocationRequest.create(), pendingIntent) // use pending intent for background, callback for foreground process
//        //                .addOnCompleteListener(new OnCompleteListener<Location>() {
//        //                    @Override
//        //                    public void onComplete(@NonNull Task<Location> task) {
//        //                        if (task.isSuccessful() && task.getResult() != null) {
//        //                            Location location = task.getResult();
//        //                            Timber.d("Result: " + location);
//        //                        }
//        //                    }
//        //                });
//    }
}