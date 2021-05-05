package com.example.aimsapp.views.currentTrip

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.database.tripDatabase.Trip
import com.example.aimsapp.databinding.ListItemTripBinding

class TripAdapter(
    private val clickListener: TripListener,
    private val onBreak: Boolean,
    private val tripListener: TripListener
) : ListAdapter<Trip, TripAdapter.ViewHolder>(TripDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TripAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, onBreak, tripListener)
    }

    class ViewHolder private constructor(private val binding: ListItemTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Trip,
            clickListener: TripListener,
            onBreak: Boolean,
            tripListener: TripListener
        )
        {
            binding.trip = item
            binding.clickListener = if (onBreak) tripListener else clickListener
            binding.tripTitle.text =
                "Trip Name: ${item.tripName} \nTrip Id: ${item.tripId}"
            binding.driverDetail.text = "Driver Name: ${item.driverName} Truck Id: ${item.truckId}"
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTripBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class TripDiffCallBack : DiffUtil.ItemCallback<Trip>() {
    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem.tripId == newItem.tripId
    }

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem == newItem
    }

}


class TripListener(val clickListener: (trip: Trip) -> Unit)
{
    fun onClick(trip: Trip) = clickListener(trip)
}