package com.example.locationservice.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationservice.databinding.RecyclerListBinding
import com.example.locationservice.model.LocationInfo

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

    private var oldData = emptyList<LocationInfo>()

    class MyViewHolder(val binding: RecyclerListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.longitude.text = oldData[position].long.toString()
        holder.binding.latitude.text = oldData[position].lat.toString()
        holder.binding.date.text = oldData[position].currentTime
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(newData: List<LocationInfo>) {
        oldData = newData
        notifyDataSetChanged()
    }

}