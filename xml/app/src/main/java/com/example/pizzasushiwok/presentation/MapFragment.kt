package com.example.pizzasushiwok.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.pizzasushiwok.R
import com.example.pizzasushiwok.databinding.FragmentMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: MapView

    private var changeMarker: Boolean = true

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startLocation()
            } else {
                Toast.makeText(requireContext(), "permission is not granted", Toast.LENGTH_SHORT)
                    .show()

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Configuration.getInstance().userAgentValue = requireContext().packageName
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        checkPermissions()
        super.onStart()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapView.generateViewId()
        map = binding.mapView
        map.setMultiTouchControls(true)

        val geoPoint1 = GeoPoint(55.7558, 37.6173)
        val marker1 = Marker(map)
        marker1.position = geoPoint1
        marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        val geoPoint2 = GeoPoint(55.751244, 37.618423)
        val marker2 = Marker(map)
        marker2.position = geoPoint2
        marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


        map.controller.setCenter(geoPoint1)
        map.controller.setZoom(12.0)

        map.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                map.overlays.add(marker1)

                val x = event.x.toInt()
                val y = event.y.toInt()
                val projection = map.projection
                val newGeoPoint = projection.fromPixels(x, y)


                if (changeMarker) {
                    marker1.position = newGeoPoint as GeoPoint?
                } else {
                    marker2.position = newGeoPoint as GeoPoint?
                }
                map.invalidate()
                true
            } else {
                false
            }
        }

        binding.centerBtn.setOnClickListener {

            if (changeMarker) {
                changeMarker = false
                marker2.setVisible(true)
                map.overlays.add(marker2)
            } else {
                changeMarker = true
                marker2.setVisible(false)
                map.invalidate()
            }
        }

        binding.focusChbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val line = Polyline()
                line.addPoint(marker1.position)
                line.addPoint(marker2.position)
                line.color = Color.RED
                map.overlays.add(line)
                map.invalidate()
            } else {
                val lineToRemove = map.overlays.find { it is Polyline } as Polyline?
                lineToRemove?.let {
                    map.overlays.remove(it)
                    map.invalidate()
                }
            }
        }

        binding.menuBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_categoryFragment)
        }
    }

    private fun startLocation() {
        val moscowLat = 55.7558
        val moscowLon = 37.6173

        val mapController = map.controller
        mapController.animateTo(GeoPoint(moscowLat, moscowLon))
    }

    private fun checkPermissions() {
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUIRED_PERMISSIONS)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

}