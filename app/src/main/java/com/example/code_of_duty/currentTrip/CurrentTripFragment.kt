package com.example.code_of_duty.currentTrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.code_of_duty.R
import com.example.code_of_duty.databinding.FragmentCurrentTripBinding
import com.example.code_of_duty.tripDatabase.TripDatabase


class CurrentTripFragment : Fragment(){

    private lateinit var binding: FragmentCurrentTripBinding
    private lateinit var viewModel: CurrentTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current_trip,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).tripDao
        val viewModelFactory = CurrentTripViewModelFactory(dataSource,application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentTripViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = CurrentTripAdapter()
        val pointAdapter = PointAdapter()


        binding.tripRecyclerview.adapter = adapter

        viewModel.trips.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        binding.tripRecyclerview2.adapter = pointAdapter

        viewModel.points.observe(viewLifecycleOwner, Observer {
            it?.let{
                pointAdapter.submitList(it)
            }
        })



        return binding.root
    }
}