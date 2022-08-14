package com.mxrwn.androidfinal.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mxrwn.androidfinal.databinding.FragmentAddBinding
import java.util.concurrent.TimeUnit


class AddFragment : Fragment() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var _binding: FragmentAddBinding;
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.button2.setOnClickListener {
            if(isLocationPermissionGranted()){
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                Log.d("7889999", isLocationPermissionGranted().toString())

            }
        }


        binding.button3.setOnClickListener {
            val place =  HashMap<String, String>()
            place["place"] = binding.editTextTextPersonName2.text.toString()
            place["long"] = binding.editTextNumber.text.toString()
            place["lat"] = binding.editTextNumber2.text.toString()
            firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            place["user"] = currentUser?.email.toString()
            db.collection("places")
                .add(place)
                .addOnSuccessListener {
                    Log.d("DOCUMENT", it.id)
                }

        }


        return binding.root

    }


    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1000
            )
            false
        } else {
            true
        }
    }




}