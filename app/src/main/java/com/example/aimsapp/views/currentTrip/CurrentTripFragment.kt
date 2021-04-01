package com.example.aimsapp.views.currentTrip

import android.app.Activity
import android.os.Bundle
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentCurrentTripsBinding
import com.example.aimsapp.views.tripDetailFragment.TripDetailFragment
import kotlinx.android.synthetic.main.list_item_trip.view.*


class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripsBinding
    private lateinit var viewModel: CurrentTripViewModel
    private lateinit var myContext: FragmentActivity


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


        val adapter = TripAdapter( TripListener { tripId ->
            val dialog = TripDetailFragment()
            dialog.show(myContext.supportFragmentManager, "Trip Detail dialog")
//            Toast.makeText(requireContext(), "$tripId", Toast.LENGTH_SHORT).show()
        } )

        binding.tripRecyclerView.adapter = adapter

        viewModel.trips.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        return binding.root
    }

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }
}