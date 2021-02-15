package com.example.code_of_duty.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.code_of_duty.R
import com.example.code_of_duty.database.SavedLocationDao
import com.example.code_of_duty.database.SavedLocationDatabase
import com.example.code_of_duty.databinding.LocationCheckBinding

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class Location : Fragment(), LocationListener {

    companion object {
        fun newInstance() = Location()
    }

    private lateinit var viewModel: LocationViewModel
    private lateinit var binding: LocationCheckBinding
    private var locationManager : LocationManager? = null
    private var isGPSEnabled = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.location_check, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SavedLocationDatabase.getInstance(application).savedLocationDao

        val viewModelFactory = LocationViewModelFactory(dataSource,application)



        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationViewModel::class.java)

        binding.lifecycleOwner = this
        binding.locationViewModel = viewModel

        if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !==
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

        try {
            locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager?
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, .02f, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.saveButton.setOnClickListener(){
            viewModel.onSaveLocation(binding.editTextLocationName.text.toString(),binding.radioGroup.checkedRadioButtonId)
        }

        return binding.root
    }


    override fun onLocationChanged(location: android.location.Location) {
        viewModel.setCoord(location.latitude,location.longitude)
    }


}



