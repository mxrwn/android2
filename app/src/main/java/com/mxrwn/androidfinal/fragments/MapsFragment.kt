package com.mxrwn.androidfinal.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainer
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.databinding.FragmentDashboardBinding
import com.mxrwn.androidfinal.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {
    private lateinit var _binding: FragmentMapsBinding;
    private val binding get() = _binding!!
    private lateinit var lastlocation : Location;
    private lateinit var fusedLocation: FusedLocationProviderClient

    private fun MapActivation() {

    }

    private val callback = OnMapReadyCallback { googleMap ->
        fusedLocation = FusedLocationProviderClient(requireActivity())
        val permission = ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                101)
        }



        //fusedLocation.lastLocation.addOnSuccessListener(requireActivity()) {
            //if(it != null){
                //lastlocation = it
                //val currentLong = LatLng(it.latitude, it.longitude)
                //googleMap.addMarker(MarkerOptions().position(currentLong).title("Marker in Sydney"))
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLong, 12f))
            //}
        //}

    }
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted ->
            if(isGranted){
                fusedLocation.lastLocation.addOnSuccessListener(requireActivity()) {
                    lastlocation = it
                    val currentLong = LatLng(it.latitude, it.longitude)
                    Log.d("MAAAAAAAP", currentLong.toString())
                    OnMapReadyCallback { googleMap ->
                        googleMap.addMarker(MarkerOptions().position(currentLong).title("Marker in Sydney"))
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLong, 12f))

                    }


                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        binding.listToggle.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mapsFragment2_to_dashboardFragment)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}