package com.example.code_of_duty.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private val SHARED_PREFS:String = "sharedPrefs"
    private val EDIT_TEXT: String = "editText"
    private val RADIO_ID: String = "radioId"


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

        retrieveInputData()

        accessLocationPermission()


        binding.saveButton.setOnClickListener(){
            viewModel.onSaveLocation(binding.editTextLocationName.text.toString(),binding.radioGroup.checkedRadioButtonId)
            binding.editTextLocationName.setText("")
            binding.radioGroup.check(R.id.indoor)
        }

        return binding.root
    }

    private fun accessLocationPermission(){
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
    }


    override fun onLocationChanged(location: android.location.Location) {
        viewModel.setCoord(location.latitude,location.longitude)
    }

    override fun onStop() {
        super.onStop()
        saveInputData()
    }

    private fun saveInputData(){
        val sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString(EDIT_TEXT, binding.editTextLocationName.text.toString())
            putInt(RADIO_ID, binding.radioGroup.checkedRadioButtonId)
        }
        editor.commit()
    }

    private fun retrieveInputData(){
        val sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        var savedText = sharedPreferences.getString(EDIT_TEXT, null)
        var savedId  = sharedPreferences.getInt(RADIO_ID, -1)

        binding.editTextLocationName.setText(savedText)
        when(savedId){
            R.id.indoor -> binding.indoor.isChecked = true
            R.id.outdoor -> binding.outdoor.isChecked = true
        }
    }
}



