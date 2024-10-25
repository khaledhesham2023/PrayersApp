package com.khaledamin.prayerapplication.presentation.screens.home

import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.location.Geocoder
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.databinding.FragmentHomeBinding
import com.khaledamin.prayerapplication.domain.model.Day
import com.khaledamin.prayerapplication.domain.model.Timing
import com.khaledamin.prayerapplication.presentation.abstracts.BaseFragment
import com.khaledamin.prayerapplication.utils.Constants
import com.khaledamin.prayerapplication.utils.State
import com.khaledamin.prayerapplication.utils.getBeginningOfTheDay
import com.khaledamin.prayerapplication.utils.getNextPrayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_home
    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    private lateinit var timingsAdapter: TimingsAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var index = 0
    private lateinit var weekDays: ArrayList<Day>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weekDays = ArrayList()
        timingsAdapter = TimingsAdapter(ArrayList())
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        checkPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewBinding.prayersList.adapter = timingsAdapter
    }

    override fun setupObservers() {
        viewModel.getPrayersLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    weekDays = state.data!!
                    viewBinding.day = state.data[index]
                    state.data[index].let { timingsAdapter.updateWith(it.timings) }
                    viewBinding.prayerName.text =
                        getNextPrayer(requireContext(), state.data[0].timings)
                    lifecycleScope.launch {
                        countDownTimer(viewBinding.prayerTimeLeft, state.data[0].timings)
                    }
                    viewBinding.progress.visibility = View.GONE
                    configureViews()
                }

                is State.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    viewBinding.progress.visibility = View.GONE
                }
            }
        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.progress.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
            }
        }
        viewModel.updateDayLiveData.observe(viewLifecycleOwner) {
            viewBinding.day = weekDays[index]
            configureViews()
        }

    }

    private fun countDownTimer(textView: TextView, timings: ArrayList<Timing>) {
        val now = System.currentTimeMillis()
        val nextTiming = timings.firstOrNull { it.time > now }
        if (nextTiming == null) {
            textView.text = getString(R.string.waiting_for_next_prayer)
            return
        }

        // Start countdown with the remaining time for the first upcoming timing
        startCountDown(textView, nextTiming.time - now, timings, timings.indexOf(nextTiming))
    }

    private fun startCountDown(
        textView: TextView,
        remainingTime: Long,
        timings: ArrayList<Timing>,
        index: Int
    ) {
        object : CountDownTimer(remainingTime, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                textView.text = getString(R.string.countdown_timer, hours, minutes, seconds)
            }

            override fun onFinish() {
                // Move to the next timing if available
                val nextIndex = index + 1
                if (nextIndex < timings.size) {
                    val nextTiming = timings[nextIndex]
                    val now = System.currentTimeMillis()
                    val newRemainingTime = nextTiming.time - now
                    startCountDown(textView, newRemainingTime, timings, nextIndex)
                } else {
                    // No more timings left, show final message
                    textView.text = getString(R.string.waiting_for_next_prayer)
                }
            }
        }.start()
    }

    private fun setupListeners() {
        viewBinding.showQiblaBtn.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMapsFragment(
                    latitude.toFloat(),
                    longitude.toFloat()
                )
            )
        }
        viewBinding.forward.setOnClickListener {
            index++
            viewModel.updateDay(index)
            timingsAdapter.updateWith(weekDays[index].timings)
        }
        viewBinding.backward.setOnClickListener {
            index--
            viewModel.updateDay(index)
            timingsAdapter.updateWith(weekDays[index].timings)
        }
    }

    private fun checkPermission() {
        if (isPermissionGranted()) {
            getLastLocation()
        } else {
            requestPermission()
            checkPermission()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    viewModel.getWeekPrayers(
                        year = Calendar.getInstance().get(Calendar.YEAR),
                        month = Calendar.getInstance().get(Calendar.MONTH) + 1,
                        latitude = latitude,
                        longitude = longitude
                    )
                    viewBinding.address.text = getCityName()
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), Constants.REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.permission_required),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCityName(): String {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 3)
            val city = "${addresses?.get(0)?.subAdminArea}, ${addresses?.get(0)?.countryCode}, ${
                addresses?.get(0)?.thoroughfare
            }"
            return city
        } catch (e: Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
        }
        return ""
    }

    private fun configureViews() {
        when (index) {
            0 -> {
                viewBinding.backward.apply {
                    visibility = View.INVISIBLE
                    isEnabled = false
                }
                viewBinding.forward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }

            weekDays.size - 1 -> {
                viewBinding.forward.apply {
                    visibility = View.INVISIBLE
                    isEnabled = false
                }
                viewBinding.backward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }

            else -> {
                viewBinding.forward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
                viewBinding.backward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }
        }
    }
}