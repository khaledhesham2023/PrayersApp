package com.khaledamin.prayerapplication.presentation.screens.home

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.databinding.FragmentHomeBinding
import com.khaledamin.prayerapplication.domain.model.Timing
import com.khaledamin.prayerapplication.presentation.abstracts.BaseFragment
import com.khaledamin.prayerapplication.utils.Constants
import com.khaledamin.prayerapplication.utils.State
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Date

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_home
    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    private lateinit var timingsAdapter: TimingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timingsAdapter = TimingsAdapter(ArrayList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewBinding.prayersList.adapter = timingsAdapter
        viewModel.getWeekPrayers(
            year = Calendar.getInstance().get(Calendar.YEAR),
            month = Calendar.getInstance().get(Calendar.MONTH) + 1,
            latitude = 31.03351631125922,
            longitude = 31.3581660222971
        )
    }

    override fun setupObservers() {
        viewModel.getPrayersLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    viewBinding.day = state.data?.get(0)
                    state.data?.get(0).let { timingsAdapter.updateWith(it!!.timings) }
                    viewBinding.nextPrayer.text = prayerChecker(state.data?.get(0)!!.timings)
                    viewBinding.progress.visibility = View.GONE
                }
                is State.Error -> {
                    Toast.makeText(requireContext(),state.message,Toast.LENGTH_SHORT).show()
                    viewBinding.progress.visibility = View.GONE
                }
            }
        }
        viewModel.showProgress.observe(viewLifecycleOwner){
            if (it){
                viewBinding.progress.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
            }
        }
    }

    private fun setupListeners() {
        viewBinding.showQiblaBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMapsFragment())
        }
    }
    private fun prayerChecker(prayers: ArrayList<Timing>):String {
        val currentTime = System.currentTimeMillis()
        for (prayer in prayers){
        }
        return getString(R.string.no_upcoming_prayers)
    }

}