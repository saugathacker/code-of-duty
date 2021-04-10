package com.example.aimsapp.views.map

import android.Manifest
import android.content.Context
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
import com.here.android.mpa.common.OnEngineInitListener
import com.here.android.mpa.mapping.AndroidXMapFragment
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapRoute
import com.here.android.mpa.routing.*


class MapFragment : Fragment(), LocationListener {


    private lateinit var binding: FragmentMapBinding
    private var mapRoute: MapRoute? = null
    private var map: Map? = null
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
                lastLocation?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
                    map?.setCenter(
                        it, Map.Animation.NONE
                    )
                }
                map?.setZoomLevel(13.5)
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

    private fun myLocation() {
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
        lastLocation?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
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
        routeOptions.routeType = RouteOptions.Type.SHORTEST
        /* Calculate 1 route. */
        routeOptions.routeCount = 1
        /* Exclude routing zones. */


        /* Finally set the route option */
        routePlan.routeOptions = routeOptions

        /* Define waypoints for the route */

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
        /* START: South of Berlin */
        val startPoint = lastLocation?.let { GeoCoordinate(it.latitude, it.longitude) }?.let {
            RouteWaypoint(it)
        }

        /* END: North of Berlin */
        val destination =
            RouteWaypoint(GeoCoordinate(32.528953697097066, -92.07248642198665))

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
                        val route =
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

}