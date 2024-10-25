package com.khaledamin.prayerapplication.presentation.screens.home

import androidx.recyclerview.widget.DiffUtil
import com.khaledamin.prayerapplication.domain.model.Timing

class TimingsUtils(
    private val oldTimings: ArrayList<Timing>,
    private val newTimings: ArrayList<Timing>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTimings.size

    override fun getNewListSize(): Int = newTimings.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTimings[oldItemPosition].name == newTimings[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTimings[oldItemPosition] == newTimings[newItemPosition]
    }
}