package com.khaledamin.prayerapplication.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.databinding.ItemTimingBinding
import com.khaledamin.prayerapplication.domain.model.Timing

class TimingsAdapter(private val timings: ArrayList<Timing>) :
    RecyclerView.Adapter<TimingsAdapter.TimingsViewHolder>() {
    inner class TimingsViewHolder(val binding: ItemTimingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimingsViewHolder {
        return TimingsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_timing,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = timings.size

    override fun onBindViewHolder(holder: TimingsViewHolder, position: Int) {
        holder.binding.timing = timings[position]
    }

    fun updateWith(newTimings: ArrayList<Timing>){
        val timingsUtils = DiffUtil.calculateDiff(TimingsUtils(timings,newTimings))
        timings.clear()
        timings.addAll(newTimings)
        timingsUtils.dispatchUpdatesTo(this)
    }
}