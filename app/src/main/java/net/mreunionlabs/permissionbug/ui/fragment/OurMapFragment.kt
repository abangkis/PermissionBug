package net.mreunionlabs.permissionbug.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import net.mreunionlabs.permissionbug.R
import timber.log.Timber


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OurMapFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OurMapFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OurMapFragment : Fragment(), OnMapReadyCallback {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OurMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (childFragmentManager.findFragmentById(R.id.gMap) as SupportMapFragment?)?.getMapAsync(this)
        val mapFragment = childFragmentManager.findFragmentById(R.id.gMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Context)
        // fixme uncomment getLastLocation to reproduce the bug
//        getLastLocation()
//        childFragmentManager.executePendingTransactions()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() = runWithPermissions(Manifest.permission.ACCESS_COARSE_LOCATION) {
        Timber.d("GET LAST LOCATION")
        val locationClient = fusedLocationClient
        locationClient?.lastLocation?.addOnSuccessListener {
            // Got last known location. In some rare situations this can be null.
            Timber.d("last location $it")
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        //        gMap.setMyLocationEnabled(true); fixme enable my location. Use fragment in regular map activity
        Timber.d("onMapReady")
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KomuttaLocationUtil.JAKARTA_BOUNDS.center, vm.getZoom().toFloat()))
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}