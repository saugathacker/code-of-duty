package com.example.aimsapp.views.waypointDetailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.aimsapp.R
import com.example.aimsapp.views.waypointDetailFragment.WayPointDetailsFragmentArgs
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentWaypointDetailsBinding

class WayPointDetailsFragment : Fragment()
{
    private lateinit var binding: FragmentWaypointDetailsBinding
    private lateinit var viewModel: WayPointDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_waypoint_details, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val wayPoint = WayPointDetailsFragmentArgs.fromBundle(
            requireArguments()
        ).selectedWayPoint
        val viewModelFactory =
            WayPointDetailsViewModelFactory(
                wayPoint,
                dataSource,
                application
            )

        viewModel = ViewModelProvider(this, viewModelFactory).get(WayPointDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}