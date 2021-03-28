package com.example.code_of_duty.currentTrip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.code_of_duty.databinding.ListItemPointBinding
import com.example.code_of_duty.tripDatabase.Point

class PointAdapter : ListAdapter<Point, PointAdapter.ViewHolder>(PointDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PointAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ListItemPointBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Point){
            binding.point =item
            var string = "${item.waypointTypeDescription.trim()}: ${item.destinationName.trim()}"
            binding.name.text = string
            string = "${item.address1.trim()}, ${item.city.trim()}, ${item.stateAbbrev.trim()} ${item.postalCode}"
            binding.address.text = string
            string = "Requested Product: ${item.productDesc} Requested Quantity: ${item.requestedQty.toString()}"
            binding.gas.text = string
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPointBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class PointDiffCallback: DiffUtil.ItemCallback<Point>() {
    override fun areItemsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem.seqNum == newItem.seqNum
    }

    override fun areContentsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem == newItem
    }
}
