package com.example.aimsapp.views.tripDetailFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.databinding.ListItemWayPointBinding

class WayPointAdapter : ListAdapter<WayPoint, WayPointAdapter.ViewHolder>(WayPointDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WayPointAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WayPointAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ListItemWayPointBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: WayPoint){
            binding.wayPoint = item
            binding.pointTitle.text = item.destinationName
            val string = "${item.address1.trim()}, ${item.city.trim()}, ${item.stateAbbrev.trim()} ${item.postalCode}"
            binding.pointDetail.text = string
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWayPointBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class WayPointDiffCallBack: DiffUtil.ItemCallback<WayPoint>(){
    override fun areItemsTheSame(oldItem: WayPoint, newItem: WayPoint): Boolean {
        return oldItem.seqNum == newItem.seqNum
    }

    override fun areContentsTheSame(oldItem: WayPoint, newItem: WayPoint): Boolean {
        return oldItem == newItem
    }

}