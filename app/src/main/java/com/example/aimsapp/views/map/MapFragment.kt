package com.example.aimsapp.views.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentMapBinding
import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.GeoPosition
import com.here.android.mpa.common.OnEngineInitListener
import com.here.android.mpa.guidance.NavigationManager
import com.here.android.mpa.guidance.NavigationManager.*
import com.here.android.mpa.guidance.TrafficUpdater
import com.here.android.mpa.mapping.AndroidXMapFragment
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapRoute
import com.here.android.mpa.routing.*
import java.lang.ref.WeakReference


class MapFragment : Fragment(), LocationListener {


    private lateinit var binding: FragmentMapBinding
    private var mapRoute: MapRoute? = null
    private var map: Map? = null
    private lateinit var route:Route
    private lateinit var requestInfo: TrafficUpdater.RequestInfo
    private lateinit var navigationManager: NavigationManager
    private var m_foregroundServiceStarted = false


    private var mapFragment: AndroidXMapFragment? = null
    private var locationManager: LocationManager? = null
    private var lastLocation: Location? = null
    private var lat = 0.0
    private var long = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        accessLocationPermission()

        binding.myLocation.setOnClickListener {
            myLocation()
        }

        binding.routeButton.setOnClickListener {
            mapRoute?.let { it1 -> map?.removeMapObject(it1) }
            createRoute()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMap()

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

    private fun initMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.mapfragment) as AndroidXMapFragment
        mapFragment!!.init(OnEngineInitListener { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                binding.textView3.visibility = View.GONE
                map = mapFragment!!.getMap()
                mapFragment?.positionIndicator?.isVisible = true

//                map?.setCenter(
//                    GeoCoordinate(32.52884909203771, -92.07249882714723),
//                    Map.Animation.NONE
//                )
                getLastLocation()?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
                    map?.setCenter(
                        it, Map.Animation.NONE
                    )
                }
                map?.setZoomLevel(14.1)

                navigationManager = getInstance()
            } else {
                Toast.makeText(requireContext(), "${error.toString()}", Toast.LENGTH_SHORT).show()
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

        getLastLocation()?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
            map?.setCenter(
                it, Map.Animation.LINEAR
            )
        }
        map?.setZoomLevel(14.2)
    }

    private fun createRoute() {
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

        /* END: ULM or RaceWay at Rayville */
        val destination =
            RouteWaypoint(GeoCoordinate(32.455296514116505, -91.75838102713207))

        /* Add both waypoints to the route plan */
        startPoint?.let { routePlan.addWaypoint(it) }
        routePlan.addWaypoint(destination)

        /* Trigger the route calculation,results will be called back via the listener */
        coreRouter.calculateRoute(
            routePlan,
            object : Router.Listener<List<RouteResult>, RoutingError> {
                override fun onProgress(i: Int) {
                    /* The calculation progress can be retrieved in this callback. */
                }

                override fun onCalculateRouteFinished(
                    routeResults: List<RouteResult>,
                    routingError: RoutingError
                ) {
                    /* Calculation is done. Let's handle the result */
                    if (routingError == RoutingError.NONE) {
                        route =
                            routeResults[0].route

                        /* Create a MapRoute so that it can be placed on the map */
                        mapRoute = MapRoute(route)

                        /* Show the maneuver number on top of the route */
                        mapRoute?.isManeuverNumberVisible = true

                        /* Add the MapRoute to the map */
                        map?.addMapObject(mapRoute!!)

                        /*
                             * We may also want to make sure the map view is orientated properly
                             * so the entire route can be easily seen.
                             */
                        route.boundingBox?.let {
                            map?.zoomTo(
                                it,
                                Map.Animation.NONE,
                                Map.MOVE_PRESERVE_ORIENTATION
                            )
                        }

                        calculateTrafficTTA()
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

    private fun calculateTrafficTTA() {
        /* Turn on traffic updates */
        TrafficUpdater.getInstance().enableUpdate(true)

        requestInfo = TrafficUpdater.getInstance().request(
            route, TrafficUpdater.Listener {
                val ttaDownloaded: RouteTta? = route.getTtaUsingDownloadedTraffic(
                    Route.WHOLE_ROUTE
                )
                if(isAdded){
                    requireActivity().runOnUiThread {
                        val duration = ttaDownloaded?.duration?.div(120)
                        binding.tTA.text = "${duration.toString()} mins"
                    }
                }

            })
    }

    private fun startNavigation() {

        /* Configure Navigation manager to launch navigation on current map */
        navigationManager.setMap(map)
        // show position indicator
        // note, it is also possible to change icon for the position indicator

        /*
         * Start the turn-by-turn navigation.Please note if the transport mode of the passed-in
         * route is pedestrian, the NavigationManager automatically triggers the guidance which is
         * suitable for walking. Simulation and tracking modes can also be launched at this moment
         * by calling either simulate() or startTracking()
         */

        /* Choose navigation modes between real time navigation and simulation */
        val alertDialogBuilder =
            AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setTitle("Navigation")
        alertDialogBuilder.setMessage("Choose Mode")
        alertDialogBuilder.setNegativeButton(
            "Navigation"
        ) { dialoginterface, i ->
            navigationManager.startNavigation(route)
            map?.tilt = 60f
            startForegroundService()
        }
        alertDialogBuilder.setPositiveButton(
            "Simulation"
        ) { dialoginterface, i ->
            navigationManager.simulate(route, 60) //Simualtion speed is set to 60 m/s
            map?.setTilt(60f)
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
        navigationManager.setMapUpdateMode(MapUpdateMode.ROADVIEW)

        /*
         * NavigationManager contains a number of listeners which we can use to monitor the
         * navigation status and getting relevant instructions.In this example, we will add 2
         * listeners for demo purpose,please refer to HERE Android SDK API documentation for details
         */
        addNavigationListeners()
    }

    private fun addNavigationListeners() {

        /*
         * Register a NavigationManagerEventListener to monitor the status change on
         * NavigationManager
         */
        navigationManager.addNavigationManagerEventListener(
            WeakReference(
                navigationManagerEventListener
            )
        )

        /* Register a PositionListener to monitor the position updates */
        navigationManager.addPositionListener(
            WeakReference(m_positionListener)
        )
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
//                Toast.makeText(requireActivity(), "Running state changed", Toast.LENGTH_SHORT).show()
            }

            override fun onNavigationModeChanged() {
//                Toast.makeText(requireActivity(), "Navigation mode changed", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded(navigationMode: NavigationManager.NavigationMode) {
//                Toast.makeText(
//                    requireActivity(),
//                    "$navigationMode was ended",
//                    Toast.LENGTH_SHORT
//                ).show()
                stopForegroundService()
            }

            override fun onMapUpdateModeChanged(mapUpdateMode: MapUpdateMode) {
//                Toast.makeText(
//                   requireActivity(), "Map update mode is changed to $mapUpdateMode",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onRouteUpdated(route: Route) {
//                Toast.makeText(requireActivity(), "Route updated", Toast.LENGTH_SHORT).show()
            }

            override fun onCountryInfo(s: String, s1: String) {
//                Toast.makeText(
//                    requireActivity(), "Country info updated from $s to $s1",
//                    Toast.LENGTH_SHORT
//                ).show()
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
            requireActivity().getApplicationContext().startService(startIntent)
        }
    }

    private fun stopForegroundService() {
        if (m_foregroundServiceStarted) {
            m_foregroundServiceStarted = false
            val stopIntent = Intent(requireActivity(), ForegroundService::class.java)
            stopIntent.action = ForegroundService.STOP_ACTION
            requireActivity().getApplicationContext().startService(stopIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /* Stop the navigation when app is destroyed */
        if (navigationManager != null) {
            stopForegroundService()
            navigationManager.stop()
        }

    }



}