package com.example.aimsapp.views.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.SupportMapFragment
import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.OnEngineInitListener
import com.here.android.mpa.guidance.NavigationManager
import com.here.android.mpa.mapping.AndroidXMapFragment
import com.here.android.mpa.mapping.Map


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private  var map: Map? = null
    private  var mapFragment: AndroidXMapFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map,container, false )


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMap()

    }

    private fun initMap(){
        mapFragment = childFragmentManager.findFragmentById(R.id.mapfragment) as AndroidXMapFragment
        mapFragment!!.init(OnEngineInitListener { error ->
            if (error == OnEngineInitListener.Error.NONE) {
                Log.i("OK","Inside if")
                map = mapFragment!!.getMap()
                map?.setCenter(
                    GeoCoordinate(49.259149, -123.008555),
                    Map.Animation.NONE
                )
            }
            else{
                Toast.makeText(requireContext(),"${error.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}