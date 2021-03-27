package com.example.code_of_duty.currentTrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.code_of_duty.R
import com.example.code_of_duty.tripDatabase.Trip

class CurrentTripAdapter : ListAdapter<Trip, CurrentTripAdapter.ViewHolder>(TripDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tripName: TextView = itemView.findViewById(R.id.trip_item)
        private val driverName: TextView = itemView.findViewById(R.id.driver_name)

        fun bind(item: Trip) {

            tripName.text = "Tripname: ${item.tripName} with Trip Id: ${item.tripId} "
            driverName.text = "Driver Name: ${item.driverName.trim()} with Truck Id: ${item.truckId}"
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_current_trip, parent, false)

                return ViewHolder(view)
            }
        }

    }

}

class TripDiffCallback : DiffUtil.ItemCallback<Trip>() {
    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem.tripId == newItem.tripId
    }

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem == newItem
    }
}