package com.example.aimsapp.views.currentTrip

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.TripDatabase
import com.example.aimsapp.databinding.FragmentCurrentTripsBinding


class CurrentTripFragment : Fragment() {

    private lateinit var binding: FragmentCurrentTripsBinding
    private lateinit var viewModel: CurrentTripViewModel
    private lateinit var myContext: FragmentActivity
    private lateinit var adapter: TripAdapter

    private lateinit var sharedPreferences: SharedPreferences
    private var onBreak = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current_trips, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TripDatabase.getInstance(application).dao
        val viewModelFactory = CurrentTripViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentTripViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        sharedPreferences = requireActivity().getSharedPreferences("tripStatus shared prefs", Context.MODE_PRIVATE)
        onBreak = sharedPreferences.getBoolean("break", false)

        adapter = TripAdapter(TripListener {
            this.findNavController().navigate(CurrentTripFragmentDirections.actionCurrentTripToTripDetailFragment(it))
        }, onBreak, TripListener{
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("It looks like you are on a break.")
                setMessage("Go to Profile page to end the break.")
                setNegativeButton("Ok"){_,_ ->
                }
                setPositiveButton("Take me there"){_,_->
                   val frag = parentFragment
                    if (frag != null) {
                        frag.findNavController().navigate(CurrentTripFragmentDirections.actionCurrentTripToProfile())
                    }
                }
            }
            alertDialogBuilder.create().show()
        })

        binding.tripRecyclerView.adapter = adapter

        viewModel.trips.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }


        return binding.root
    }



    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }
}