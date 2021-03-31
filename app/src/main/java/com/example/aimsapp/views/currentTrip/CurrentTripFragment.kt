package com.example.aimsapp.views.currentTrip

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentCurrentTripsBinding

class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripsBinding
    private lateinit var viewModel: CurrentTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current_trips, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val viewModelFactory = CurrentTripViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentTripViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = TripAdapter()

        binding.tripRecyclerView.adapter = adapter

        viewModel.trips.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}