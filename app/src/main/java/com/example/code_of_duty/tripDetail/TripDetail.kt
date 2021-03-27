package com.example.code_of_duty.tripDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.code_of_duty.R
import com.example.code_of_duty.currentTrip.CurrentTripAdapter
import com.example.code_of_duty.currentTrip.CurrentTripViewModel
import com.example.code_of_duty.currentTrip.CurrentTripViewModelFactory
import com.example.code_of_duty.currentTrip.TripListener
import com.example.code_of_duty.databinding.FragmentCurrentTripBinding
import com.example.code_of_duty.databinding.FragmentTripDetailBinding
import com.example.code_of_duty.tripDatabase.TripDatabase

class TripDetail : Fragment() {

    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var viewModel: TripDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_trip_detail,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).tripDao

        val viewModelFactory = TripDetailViewModelFactory(dataSource,application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TripDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}