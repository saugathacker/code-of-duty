package com.example.aimsapp.views.tripDetailFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentTripDetailBinding


class TripDetailFragment : Fragment()
{
    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var viewModel: TripDetailViewModel
    private lateinit var adapter: WayPointAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_detail, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val trip = TripDetailFragmentArgs.fromBundle(requireArguments()).selectedTrip
        val viewModelFactory = TripDetailViewModelFactory(trip, dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TripDetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = WayPointAdapter(WayPointListener {
                this.findNavController().navigate(TripDetailFragmentDirections.actionTripDetailFragmentToWayPointDetailsFragment(it))

        })
        binding.wayPointRecyclerView.adapter = adapter

        viewModel.wayPoints.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}