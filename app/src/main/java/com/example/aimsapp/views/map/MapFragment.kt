@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.example.aimsapp.views.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentMapBinding
import com.example.aimsapp.views.forms.site.SiteFormDialog
import com.example.aimsapp.views.forms.source.SourceFormDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.here.android.mpa.common.*
import com.here.android.mpa.guidance.AudioPlayerDelegate
import com.here.android.mpa.guidance.LaneInformation
import com.here.android.mpa.guidance.NavigationManager
import com.here.android.mpa.guidance.NavigationManager.*
import com.here.android.mpa.guidance.TrafficUpdater
import com.here.android.mpa.mapping.AndroidXMapFragment
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapRoute
import com.here.android.mpa.routing.*
import java.lang.ref.WeakReference

/**
 * This is the Map fragment. Displays map and handles functions of the map
 */
@RequiresApi(Build.VERSION_CODES.O)
class MapFragment : Fragment(), LocationListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: MapViewModel

    private var mapRoute: MapRoute? = null
    private var map: Map? = null
    private lateinit var route:Route
    private lateinit var requestInfo: TrafficUpdater.RequestInfo
    private var navigationManager: NavigationManager? = null
    private var m_foregroundServiceStarted = false
    private var mapFragment: AndroidXMapFragment? = null

    private var locationManager: LocationManager? = null
    private var lastLocation: Location? = null

    private var lat = 0.0
    private var long = 0.0
    private var zoom = 0.0
    private var orientaion = 0.0f
    private var arr1: FloatArray? = null
    private var tripId = -1L
    private var seqNum = -1L




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        binding.myLocation.setOnClickListener {
            myLocation()
        }

//        binding.routeButton.setOnClickListener {
//            mapRoute?.let { it1 -> map?.removeMapObject(it1) }
////            createRoute()
//        }

        binding.voiceCtrlButton.setOnClickListener {
            val intent = Intent(requireActivity(), VoiceSkinsActivity::class.java)
            requireActivity().startActivity(intent)
        }

        binding.endNavigation.setOnClickListener {

            val alertDialogBuilder =
                AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setTitle("End Navigation")
            alertDialogBuilder.setMessage("Have you reached your destination?")
            alertDialogBuilder.setNegativeButton(
                "No"
            ) { dialoginterface, i ->

            }
            alertDialogBuilder.setPositiveButton(
                "Yes"
            ) { dialoginterface, i ->
                viewModel.navigationEnded()
                map?.removeMapObject(mapRoute!!)
                navigationManager?.let {
                    it.stop()
                }
                stopForegroundService()
                it.visibility = View.GONE

                viewModel.pointArrived()
                var dialog: DialogFragment
                when(viewModel.wayPoint.waypointTypeDescription){
                    "Source" -> dialog = SourceFormDialog(viewModel.wayPoint)

                    else -> dialog = SiteFormDialog(viewModel.wayPoint)
                }
               showDialogWithChildFragmentManager(dialog)
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
        arr1 = floatArrayOf(MapFragmentArgs.fromBundle(requireArguments()).latitude,MapFragmentArgs.fromBundle(requireArguments()).longitude)
        tripId = MapFragmentArgs.fromBundle(requireArguments()).ownerTripId
        seqNum = MapFragmentArgs.fromBundle(requireArguments()).seqNum
        getPoint()
        return binding.root
    }

    private fun showDialogWithChildFragmentManager(dialog: DialogFragment){
        if(isAdded){
            dialog.show(childFragmentManager,"Form")
        }
    }
    private fun getPoint() {
        if(tripId.equals(-1)){

        }
        else{
            viewModel.getPoint(tripId,seqNum)
        }
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("shared prefs", Context.MODE_PRIVATE)

        lat = sharedPreferences.getFloat("lat", 0.0F).toDouble()
        long = sharedPreferences.getFloat("long", 0.0F).toDouble()
        zoom = sharedPreferences.getFloat("zoom", 0.0F).toDouble()
        orientaion = sharedPreferences.getFloat("orientation", 0.0F)

        if(sharedPreferences.getBoolean("nav",false)){
            viewModel.navigationStarted()
        }

        Toast.makeText(requireContext(),"${viewModel.inNavigationMode}", Toast.LENGTH_SHORT).show()
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accessLocationPermission()
        if(hasPermission()){
            initMap()
        }

    }

    private fun accessLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }

        try {
            locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, .5f, this)
            lastLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hasPermission(): Boolean{

        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    private fun initMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.mapfragment) as AndroidXMapFragment
        mapFragment!!.init(OnEngineInitListener { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                binding.textView3.visibility = View.GONE
                map = mapFragment!!.map
                mapFragment?.positionIndicator?.isVisible = true

//                map?.setCenter(
//                    GeoCoordinate(32.52884909203771, -92.07249882714723),
//                    Map.Animation.NONE
//                )
                if(long == 0.0 && lat == 0.0 && zoom == 0.0 && orientaion == 0.0f){
                    getLastLocation()?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
                        map?.setCenter(
                            it, Map.Animation.NONE
                        )
                        map?.zoomLevel = 14.1
                        map?.setOrientation(0.0f)
                    }
                }
                else{
                    map?.setCenter(GeoCoordinate(lat,long), Map.Animation.NONE)
                    map?.zoomLevel = zoom
                    map?.orientation = orientaion
                }

                navigationManager = getInstance()

                if(arr1?.get(0) !== 0.0f){
                    createRoute(arr1!!)
                }
                if(viewModel.inNavigationMode){
                    /* Create a MapRoute so that it can be placed on the map */

                        viewModel.route?.let {
                            route = it
                            mapRoute = MapRoute(route)

                            /* Show the maneuver number on top of the route */
                            mapRoute?.isManeuverNumberVisible = true

                            /* Add the MapRoute to the map */
                            map?.addMapObject(mapRoute!!)


                            /* Configure Navigation manager to launch navigation on current map */
                            navigationManager?.setMap(map)

                            navigationManager?.startNavigation(route)
                            map?.tilt = 60f
                            startForegroundService()
                            /*
         * Set the map update mode to ROADVIEW.This will enable the automatic map movement based on
         * the current location.If user gestures are expected during the navigation, it's
         * recommended to set the map update mode to NONE first. Other supported update mode can be
         * found in HERE Mobile SDK for Android (Premium) API doc
         */
                            navigationManager?.mapUpdateMode = MapUpdateMode.ROADVIEW

                            /*
                             * Sets the measuring unit system that is used by voice guidance.
                             * Check VoicePackage.getCustomAttributes() to see whether selected package has needed
                             * unit system.
                             */navigationManager?.distanceUnit = UnitSystem.IMPERIAL_US

                            /*
                             * NavigationManager contains a number of listeners which we can use to monitor the
                             * navigation status and getting relevant instructions.In this example, we will add 2
                             * listeners for demo purpose,please refer to HERE Android SDK API documentation for details
                             */
                            addNavigationListeners()
                            binding.endNavigation.visibility = View.VISIBLE
                        }
                }
            } else {
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onLocationChanged(location: Location) {
        long = location.longitude
        lat = location.latitude
        mapFragment?.positionIndicator?.isVisible = true
    }

    private fun getLastLocation(): Location?{
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }

        }
        lastLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return lastLocation
    }

    private fun myLocation() {

        val lastLocation = getLastLocation()

        if (lastLocation != null) {
            map?.setCenter(GeoCoordinate(lastLocation.latitude, lastLocation.longitude),Map.Animation.BOW)
        }
        mapFragment?.positionIndicator?.isVisible = true
        map?.setZoomLevel(14.2, Map.Animation.BOW)
        map?.setTilt(0f, Map.Animation.BOW)
        map?.setOrientation(0f, Map.Animation.BOW)
    }

    private fun createRoute(arr1: FloatArray) {
        /* Initialize a CoreRouter */
        val coreRouter = CoreRouter()

        /* Initialize a RoutePlan */
        val routePlan = RoutePlan()

        /*
         * Initialize a RouteOption. HERE Mobile SDK allow users to define their own parameters for the
         * route calculation,including transport modes,route types and route restrictions etc.Please
         * refer to API doc for full list of APIs
         */
        val routeOptions = RouteOptions()
        /* Other transport modes are also available e.g Pedestrian */
        routeOptions.transportMode = RouteOptions.TransportMode.CAR
        /* Calculate the shortest route available. */
        routeOptions.routeType = RouteOptions.Type.FASTEST
        /* Calculate 1 route. */
        routeOptions.routeCount = 1
        /* Exclude routing zones. */


        /* Finally set the route option */
        routePlan.routeOptions = routeOptions

        /* Define waypoints for the route */

        /* START: My Location */
        val startPoint = getLastLocation()?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
            RouteWaypoint(it)
        }

        /* END: ULM or RaceWay at Rayville 32.525549062437904, -92.06795104556012 */
        val destination =
            RouteWaypoint(GeoCoordinate(arr1[0].toDouble(), arr1[1].toDouble()))

        /* Add both waypoints to the route plan */
        startPoint?.let { routePlan.addWaypoint(it) }
        routePlan.addWaypoint(destination)
        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Navigation")
        alertDialogBuilder.setMessage("Calculating Route")
        alertDialogBuilder.setCancelable(true)
        val alertDialog = alertDialogBuilder.create()

        /* Trigger the route calculation,results will be called back via the listener */
        coreRouter.calculateRoute(
            routePlan,
            object : Router.Listener<List<RouteResult>, RoutingError> {
                override fun onProgress(i: Int) {
                    /* The calculation progress can be retrieved in this callback. */
                    alertDialog.show()

                }

                override fun onCalculateRouteFinished(
                    routeResults: List<RouteResult>,
                    routingError: RoutingError
                ) {
                    alertDialog.dismiss()
                    /* Calculation is done. Let's handle the result */
                    if (routingError == RoutingError.NONE) {
                        route =
                            routeResults[0].route

                        /* Create a MapRoute so that it can be placed on the map */

                        mapRoute = MapRoute(route)

                        /* Show the maneuver number on top of the route */
                        mapRoute?.isManeuverNumberVisible = true

                        map?.removeAllMapObjects()
                        /* Add the MapRoute to the map */
                        map?.addMapObject(mapRoute!!)

                        /*
                             * We may also want to make sure the map view is orientated properly
                             * so the entire route can be easily seen.
                             */
                        route.boundingBox?.let {
                            map?.zoomTo(
                                it,
                                Map.Animation.BOW,
                                Map.MOVE_PRESERVE_ORIENTATION
                            )
                        }

                        viewModel.route = route
                        startNavigation()

                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Error:route calculation returned error code: $routingError",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }


    private fun startNavigation() {

        /* Configure Navigation manager to launch navigation on current map */
        navigationManager?.setMap(map)
        // show position indicator
        // note, it is also possible to change icon for the position indicator

        /*
         * Start the turn-by-turn navigation.Please note if the transport mode of the passed-in
         * route is pedestrian, the NavigationManager automatically triggers the guidance which is
         * suitable for walking. Simulation and tracking modes can also be launched at this moment
         * by calling either simulate() or startTracking()
         */
        viewModel.navigationStarted()


        /* Choose navigation modes between real time navigation and simulation */
        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Navigation")
        alertDialogBuilder.setMessage("Choose Mode")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setNegativeButton(
            "Navigation"
        ) { dialoginterface, i ->
            navigationManager?.startNavigation(route)
            map?.tilt = 60f
            startForegroundService()
        }
        alertDialogBuilder.setPositiveButton(
            "Simulation"
        ) { dialoginterface, i ->
            navigationManager?.simulate(route, 100) //Simualtion speed is set to 60 m/s
            map?.tilt = 70f
            startForegroundService()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        /*
         * Set the map update mode to ROADVIEW.This will enable the automatic map movement based on
         * the current location.If user gestures are expected during the navigation, it's
         * recommended to set the map update mode to NONE first. Other supported update mode can be
         * found in HERE Mobile SDK for Android (Premium) API doc
         */
        navigationManager?.mapUpdateMode = MapUpdateMode.ROADVIEW

        /*
         * Sets the measuring unit system that is used by voice guidance.
         * Check VoicePackage.getCustomAttributes() to see whether selected package has needed
         * unit system.
         */navigationManager?.distanceUnit = UnitSystem.IMPERIAL_US

        /*
         * NavigationManager contains a number of listeners which we can use to monitor the
         * navigation status and getting relevant instructions.In this example, we will add 2
         * listeners for demo purpose,please refer to HERE Android SDK API documentation for details
         */
        addNavigationListeners()
        binding.endNavigation.visibility = View.VISIBLE
    }

    private fun addNavigationListeners() {

        /*
         * Register a NavigationManagerEventListener to monitor the status change on
         * NavigationManager
         */
        navigationManager?.addNavigationManagerEventListener(
            WeakReference(
                navigationManagerEventListener
            )
        )


        /* Register a PositionListener to monitor the position updates */
        navigationManager?.addPositionListener(
            WeakReference(m_positionListener)
        )

        /* Register a AudioPlayerDelegate to monitor TTS text */
        navigationManager?.audioPlayer
            ?.setDelegate(m_audioPlayerDelegate)
    }

    private val m_positionListener: NavigationManager.PositionListener =
        object : NavigationManager.PositionListener() {
            override fun onPositionUpdated(geoPosition: GeoPosition) {
                /* Current position information can be retrieved in this callback */
            }
        }

    private val navigationManagerEventListener: NavigationManagerEventListener =
        object : NavigationManagerEventListener() {
            override fun onRunningStateChanged() {
                if (isAdded){
                    Toast.makeText(requireContext(), "Running state changed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNavigationModeChanged() {
                if (isAdded){
                    Toast.makeText(requireContext(), "Navigation mode changed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onEnded(navigationMode: NavigationManager.NavigationMode) {
                if (isAdded){
                    Toast.makeText(
                        requireContext(),
                        "$navigationMode was ended",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                viewModel.navigationEnded()
                map?.removeMapObject(mapRoute!!)
                stopForegroundService()
                binding.endNavigation.visibility = View.GONE
            }

            override fun onMapUpdateModeChanged(mapUpdateMode: MapUpdateMode) {
                if (isAdded){
                    Toast.makeText(
                        requireContext(), "Map update mode is changed to $mapUpdateMode",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onRouteUpdated(route: Route) {
                if (isAdded){
                    Toast.makeText(requireContext(), "Route updated", Toast.LENGTH_SHORT).show()
                }
                map?.removeMapObject(mapRoute!!)
                mapRoute = MapRoute(route)
                map?.addMapObject(mapRoute!!)
            }

            override fun onCountryInfo(s: String, s1: String) {
                if (isAdded){
                    Toast.makeText(
                        requireContext(), "Country info updated from $s to $s1",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onDestinationReached() {
                if (isAdded){
                    Toast.makeText(requireContext(), "Destination reached", Toast.LENGTH_SHORT).show()
                }
                map?.removeMapObject(mapRoute!!)
                stopForegroundService()
                viewModel.navigationEnded()
                binding.endNavigation.visibility = View.GONE
                viewModel.pointArrived()
                var dialog: DialogFragment
                when(viewModel.wayPoint.waypointTypeDescription){
                    "Source" -> dialog = SourceFormDialog(viewModel.wayPoint)

                    else -> dialog = SiteFormDialog(viewModel.wayPoint)
                }
               showDialogWithChildFragmentManager(dialog)

            }
        }

    /*
     * Android 8.0 (API level 26) limits how frequently background apps can retrieve the user's
     * current location. Apps can receive location updates only a few times each hour.
     * See href="https://developer.android.com/about/versions/oreo/background-location-limits.html
     * In order to retrieve location updates more frequently start a foreground service.
     * See https://developer.android.com/guide/components/services.html#Foreground
     */
    private fun startForegroundService() {
        if (!m_foregroundServiceStarted) {
            m_foregroundServiceStarted = true
            val startIntent = Intent(requireActivity(), ForegroundService::class.java)
            startIntent.action = ForegroundService.START_ACTION
            requireActivity().applicationContext.startService(startIntent)
        }
    }

    private fun stopForegroundService() {
        if (m_foregroundServiceStarted) {
            m_foregroundServiceStarted = false
            val stopIntent = Intent(requireActivity(), ForegroundService::class.java)
            stopIntent.action = ForegroundService.STOP_ACTION
            requireActivity().applicationContext.startService(stopIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /* Stop the navigation when app is destroyed */
        stopForegroundService()
        navigationManager?.let {
            it.stop()
        }

        val sharedPreferences = requireContext().getSharedPreferences("shared prefs", Context.MODE_PRIVATE)

        sharedPreferences.edit().apply {
            map?.center?.latitude?.toFloat()?.let { putFloat("lat", it) }
            map?.center?.longitude?.toFloat()?.let { putFloat("long", it) }
            map?.zoomLevel?.toFloat()?.let { putFloat("zoom", it) }
            map?.orientation?.let { putFloat("orientation", it) }
            putBoolean("nav",viewModel.inNavigationMode)
        }.apply()
    }

    private val m_audioPlayerDelegate: AudioPlayerDelegate = object : AudioPlayerDelegate {
        override fun playText(s: String): Boolean {
            requireActivity().runOnUiThread(Runnable {
                Toast.makeText(
                    requireActivity(),
                    "TTS output: $s",
                    Toast.LENGTH_SHORT
                ).show()
            })
            return false
        }

        /**
         * please refer to android sdk function for this overridden method
         */
        override fun playFiles(strings: Array<String>): Boolean {
            return false
        }
    }

}