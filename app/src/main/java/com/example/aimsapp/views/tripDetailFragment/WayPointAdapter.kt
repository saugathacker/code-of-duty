package com.example.aimsapp.views.tripDetailFragment

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aimsapp.R
import com.example.aimsapp.database.tripDatabase.WayPoint
import com.example.aimsapp.databinding.ListItemWayPointBinding

class WayPointAdapter(private val clickListener: WayPointListener) : ListAdapter<WayPoint, WayPointAdapter.ViewHolder>(WayPointDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WayPointAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WayPointAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(private val binding: ListItemWayPointBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: WayPoint, clickListener: WayPointListener)
        {
            binding.wayPoint = item
            binding.clickListener = clickListener
            binding.pointTitle.text = item.destinationName
            val string = "${item.address1.trim()}, ${item.city.trim()}, ${item.stateAbbrev.trim()} ${item.postalCode}"
            binding.pointDetail.text = string
            if (!item.waypointTypeDescription.equals("Source")){
                binding.typeIcon.setImageResource(R.drawable.ic_gas_site)
                binding.typeTitle.text = item.waypointTypeDescription.subSequence(0,4)
            }
            if (item.completed){
                binding.pointTitle.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
            }
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

class WayPointListener(val clickListener: (wayPoint: WayPoint) -> Unit)
{
    fun onClick(wayPoint: WayPoint) = clickListener(wayPoint)
}