package com.example.aimsapp.views.tripDetailFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.example.aimsapp.databinding.FragmentTripDetailBinding


class TripDetailFragment : DialogFragment(){

    lateinit var binding: FragmentTripDetailBinding
    lateinit var viewModel: TripDetailViewModel
    lateinit var adapter: WayPointAdapter


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_trip_detail, null, false )
        binding.close.setOnClickListener {
            dismiss()
        }
         viewModel = ViewModelProvider(this).get(TripDetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return AlertDialog.Builder(activity, R.style.DialogTheme)
            .setView(binding.root)
            .create()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.list_item_way_point, container)

        adapter = WayPointAdapter()
        binding.wayPointRecyclerView.adapter = adapter

        viewModel.wayPoints.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return rootView
    }
}