package com.example.code_of_duty.currentTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.code_of_duty.R
import com.example.code_of_duty.databinding.ListItemCurrentTripBinding
import com.example.code_of_duty.tripDatabase.Trip

class CurrentTripAdapter(private val clickListener: TripListener) : ListAdapter<Trip, CurrentTripAdapter.ViewHolder>(TripDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemCurrentTripBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Trip, clickListener: TripListener) {
            binding.trip = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCurrentTripBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
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

class TripListener(val clickListener: (tripId: Long?) -> Unit) {
    fun onClick(trip: Trip?) = clickListener(trip?.tripId)
}