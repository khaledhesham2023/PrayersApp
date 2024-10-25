package com.khaledamin.prayerapplication.presentation.screens.map

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.databinding.FragmentMapsBinding
import com.khaledamin.prayerapplication.presentation.abstracts.BaseFragment
import com.khaledamin.prayerapplication.utils.Constants
import com.khaledamin.prayerapplication.utils.State
import com.khaledamin.prayerapplication.utils.drawableToBitmap
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class MapsFragment : BaseFragment<FragmentMapsBinding, MapsViewModel>(), OnMapReadyCallback {
    override val layout: Int
        get() = R.layout.fragment_maps
    override val viewModelClass: Class<MapsViewModel>
        get() = MapsViewModel::class.java

    private var latitude = 0.0
    private var longitude = 0.0
    private var direction = 0.0
    private lateinit var locationTitle: String
    private lateinit var map: GoogleMap
    private lateinit var currentLocation: LatLng
    private lateinit var targetLocation: LatLng
    private var line: Polyline? = null
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latitude = MapsFragmentArgs.fromBundle(requireArguments()).lat.toDouble()
        longitude = MapsFragmentArgs.fromBundle(requireArguments()).long.toDouble()
        locationTitle = MapsFragmentArgs.fromBundle(requireArguments()).location
        currentLocation = LatLng(latitude, longitude)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModel.getCurrentQibla(latitude, longitude)
    }

    override fun setupObservers() {
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.progress.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
            }
        }
        viewModel.getQiblaLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    direction = state.data?.direction!!
                    val rotateAnimator =
                        ObjectAnimator.ofFloat(viewBinding.qiblaArrow, "rotation", 0f, direction.toFloat())
                    rotateAnimator.duration = 1000L
                    rotateAnimator.start()
                    viewBinding.progress.visibility = View.GONE
                }

                is State.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    viewBinding.progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        googleMap.projection.visibleRegion.latLngBounds
        map = googleMap
        val currentLocation = LatLng(latitude, longitude)
        googleMap.addMarker(
            MarkerOptions().position(currentLocation).title(locationTitle)
        )
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                currentLocation,
                Constants.STREET_VIEW
            )
        )

//        val distance = 1000.0
//        val kaabaLocation = LatLng(21.4225, 39.8262)
//        googleMap.addMarker(
//            MarkerOptions().position(kaabaLocation).title("Kaaba").icon(
//                getBitmapDescriptor(R.drawable.ic_kaaba)
//            )
//        )

//        addArrow(googleMap,currentLocation, direction, distance)

    }

    private fun startRotatingLine() {
        targetLocation = LatLng(currentLocation.latitude + 0.1, currentLocation.longitude + 0.1)
        updateLine()
    }

    private fun updateLine() {
        line?.remove()
        val visibleTarget = calculateVisibleTarget(map)
        line = map.addPolyline(
            PolylineOptions().add(currentLocation, visibleTarget).width(5f)
                .color(ContextCompat.getColor(requireContext(), R.color.dark_soft_blue))
        )
        handler.postDelayed({ updateLine() }, 100)
    }

    private fun calculateVisibleTarget(googleMap: GoogleMap): LatLng {
        val latOffset = sin(Math.toRadians(direction)) * 0.01 // Adjust distance as needed
        val lngOffset = cos(Math.toRadians(direction)) * 0.01 // Adjust distance as needed
        var projectedLocation =
            LatLng(currentLocation.latitude + latOffset, currentLocation.longitude + lngOffset)
        val bounds = googleMap.projection.visibleRegion.latLngBounds
        if (!bounds.contains(projectedLocation)) {
            if (projectedLocation.latitude < bounds.southwest.latitude) {
                projectedLocation = LatLng(bounds.southwest.latitude, projectedLocation.longitude)
            } else if (projectedLocation.latitude > bounds.northeast.latitude) {
                projectedLocation = LatLng(bounds.northeast.latitude, projectedLocation.longitude)
            }

            if (projectedLocation.longitude < bounds.southwest.longitude) {
                projectedLocation = LatLng(projectedLocation.latitude, bounds.southwest.longitude)
            } else if (projectedLocation.longitude > bounds.northeast.longitude) {
                projectedLocation = LatLng(projectedLocation.latitude, bounds.northeast.longitude)
            }
        }

        return projectedLocation
    }
}

//    private fun addArrow(
//        googleMap: GoogleMap,
//        currentLocation: LatLng,
//        direction: Double,
//        distance: Double
//    ) {
//        val endPoint = calculateEndPoint(currentLocation, direction, distance)
//
//        googleMap.addPolyline(
//            PolylineOptions().add(currentLocation).width(10f).color(R.color.black)
//        )
//        googleMap.addMarker(
//            MarkerOptions()
//                .position(endPoint)
//                .icon(getBitmapDescriptor(R.drawable.ic_qibla_arrow)) // Assuming you have an arrowhead image
//        )
//
//    }

private fun calculateEndPoint(startPoint: LatLng, direction: Double, distance: Double): LatLng {
    val earthRadius = 6371000.0 // Radius of the Earth in meters

    val directionRad = Math.toRadians(direction)
    val lat1 = Math.toRadians(startPoint.latitude)
    val lon1 = Math.toRadians(startPoint.longitude)

    val lat2 = asin(
        sin(lat1) * cos(distance / earthRadius) +
                cos(lat1) * sin(distance / earthRadius) * cos(directionRad)
    )

    val lon2 = lon1 + atan2(
        sin(directionRad) * sin(distance / earthRadius) * cos(lat1),
        cos(distance / earthRadius) - sin(lat1) * sin(lat2)
    )

    return LatLng(Math.toDegrees(lat2), Math.toDegrees(lon2))
}

//    fun getBitmapDescriptor(drawableId: Int): BitmapDescriptor {
//        // Convert drawable to bitmap
//        val bitmap = drawableToBitmap(requireContext(), drawableId)
//
//        // Scale the bitmap to a smaller size (adjust width and height as needed)
//        val scaledBitmap = bitmap?.let { scaleBitmap(it, 100, 100) } // Example size 100x100
//
//        // Create a BitmapDescriptor from the scaled bitmap
//        val bitmapDescriptor = scaledBitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
//        return bitmapDescriptor!!
//    }

//    private fun scaleBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
//        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
//    }
