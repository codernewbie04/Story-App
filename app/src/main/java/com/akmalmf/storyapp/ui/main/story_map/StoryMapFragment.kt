package com.akmalmf.storyapp.ui.main.story_map

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragmentWithObserve
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentStoryMapBinding
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.utils.toGone
import com.akmalmf.storyapp.domain.utils.toVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class StoryMapFragment : BaseFragmentWithObserve<FragmentStoryMapBinding>(), OnMapReadyCallback {
    private val viewModel: StoryMapViewModel by hiltNavGraphViewModels(R.id.story_nav)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryMapBinding
        get() = FragmentStoryMapBinding::inflate

    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    override fun initView() {

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun initObservable() {
        viewModel.stories.observe(this){
            when(it.status){
                Status.LOADING -> {
                    bi.apply {
                        map.toGone()
                        progressBar.toVisible()
                    }
                }
                Status.SUCCESS -> {
                    bi.apply {
                        map.toVisible()
                        progressBar.toGone()
                        if (it.data != null){
                            if(::mMap.isInitialized){
                                addManyMarker(it.data.listStory)
                            }
                        }
                    }

                }
                Status.ERROR -> {
                    bi.apply {
                        map.toVisible()
                        progressBar.toGone()
                    }
                    (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                snackBarError("Membutuhkan akases GPS!")
                requireActivity().finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
//        mMap.setOnPoiClickListener { pointOfInterest ->
//            val poiMarker = mMap.addMarker(
//                MarkerOptions()
//                    .position(pointOfInterest.latLng)
//                    .title(pointOfInterest.name)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//            )
//            poiMarker?.showInfoWindow()
//        }
        setMapStyle()
        getMyLocation()
        viewModel.getStoriesWithMap()
    }
//    fun onMarkerClick(marker: Marker): Boolean {
////        if (marker == myMarker) {
////            //handle click here
////        }
//    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.map_style
                    )
                )
            if (!success) {
                Log.e("DebugMAP", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("DebugMAP", "Can't find style. Error: ", exception)
        }
    }

    private fun addManyMarker(listStory: List<StoryResponse>) {

        listStory.forEach { story ->
            val latLng = LatLng(story.lat, story.lon)
            mMap.addMarker(
                MarkerOptions().position(latLng).title(story.name)
            )
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                50
            )
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        private const val REQUEST_CODE_PERMISSIONS = 11
    }
}