package com.example.aimsapp.views.tripDetailFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentTripDetailBinding
import com.example.aimsapp.views.forms.site.SiteFormDialog
import com.example.aimsapp.views.forms.source.SourceFormDialog

/**
 * This is the fragment for the trip detail
 */
class TripDetailFragment : Fragment()
{
    private lateinit var binding: FragmentTripDetailBinding
    private lateinit var viewModel: TripDetailViewModel
    private lateinit var adapter: WayPointAdapter
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * please refer to android sdk function for this overridden method
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_detail, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val trip = TripDetailFragmentArgs.fromBundle(requireArguments()).selectedTrip
        val viewModelFactory = TripDetailViewModelFactory(trip, dataSource,application)
        sharedPreference = requireActivity().getSharedPreferences("tripsStatus shared prefs", Context.MODE_PRIVATE)
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

        //if trip is already started
        if (trip.started){
            binding.startTrip.text = "Resume Trip"
            binding.startTrip.setOnClickListener {
                //getting the next incomplete trip
                val point = viewModel.getNextWayPoint()
                if (point != null) {
                    //if the next trip is not started
                    if(!point.started){
                        point.started = true
                        viewModel.updatePoint(point)
                    }
                    //if the next trip is already started and arrived
                    if(point.arrived){
                        val dialog: DialogFragment
                        when(point.waypointTypeDescription){
                            "Source" -> dialog = SourceFormDialog(point)
                            else -> dialog = SiteFormDialog(point)
                        }
                        dialog.show(childFragmentManager, "Forms")
                    }
                    //if the next trip is already started but not arrived
                    else{
                        this.findNavController().navigate(TripDetailFragmentDirections.actionTripDetailFragmentToMap().setLatitude(point.latitude.toFloat()).setLongitude(point.longitude.toFloat()).setOwnerTripId(point.ownerTripId).setSeqNum(point.seqNum))
                    }
                }
                else{
                    binding.startTrip.text = "Trip Completed"
                    binding.startTrip.isEnabled = false
                }
            }
            //if the trip is complete
            if(trip.completed){
                binding.startTrip.text = "Trip Completed"
                binding.startTrip.isEnabled = false
            }
        }
        else{
            binding.startTrip.setOnClickListener {
                viewModel.startTrip()
                val point = viewModel.getNextWayPoint()
                if (point != null) {
                    this.findNavController().navigate(TripDetailFragmentDirections.actionTripDetailFragmentToMap().setLatitude(point.latitude.toFloat()).setLongitude(point.longitude.toFloat()).setOwnerTripId(point.ownerTripId).setSeqNum(point.seqNum))
                }
            }
        }

        viewModel.wayPoints.observe(viewLifecycleOwner, Observer {
            viewModel.updateStats(it)
            adapter.notifyDataSetChanged()
        })

        if(trip.started)
        {
            if(trip.completed){
                binding.statusView.setImageResource(R.drawable.ic_completed)
            }
            else{
                binding.statusView.setImageResource(R.drawable.ic_inprogress)
            }
        }
        return binding.root
    }
}