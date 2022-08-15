package com.mxrwn.androidfinal.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.databinding.FragmentAddBinding
import java.io.File
import java.net.URI
import java.util.*


class AddFragment : Fragment() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var _binding: FragmentAddBinding;
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    lateinit var observer : MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addImage.setOnClickListener {
            observer.selectImage()

            Log.d("LAT", lifecycle.currentState.toString())
        }

        binding.button2.setOnClickListener {
            @SuppressLint("MissingPermission")
            if(isLocationPermissionGranted()){
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                Log.d("7889999", isLocationPermissionGranted().toString())
                fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, @SuppressLint(
                    "MissingPermission"
                )
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                    .addOnSuccessListener { location: Location? ->
                        if (location == null)

                        else {
                            val lat = location.latitude
                            val lon = location.longitude
                            binding.editTextNumber.text = Editable.Factory.getInstance().newEditable(location.latitude.toString())
                            binding.editTextNumber2.text = Editable.Factory.getInstance().newEditable(location.longitude.toString())

                            Log.d("LAT", observer.imageUri.toString())
                        }

                    }


            }
        }


        binding.button3.setOnClickListener {
            val place =  HashMap<String, String>()
            place["place"] = binding.editTextTextPersonName2.text.toString()
            place["long"] = binding.editTextNumber.text.toString()
            place["lat"] = binding.editTextNumber2.text.toString()
            place["image"] = observer.imageUri.toString()
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

class MyLifecycleObserver(private val registry : ActivityResultRegistry)
    : DefaultLifecycleObserver {
    lateinit var getContent : ActivityResultLauncher<String>
    var imageUri : Uri? = null;
    lateinit var storage: FirebaseStorage

    override fun onCreate(owner: LifecycleOwner) {

        getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
            val storageRef: StorageReference = FirebaseStorage.getInstance().reference;

            val riversRef = storageRef.child("images/test.jpg")
            riversRef.downloadUrl.addOnSuccessListener {
                imageUri = it
            }
            val uploadTask = uri?.let { riversRef.putFile(it) }
            Log.d("fdsgdfgds", uploadTask.toString())
            // Register observers to listen for when the download is done or if it fails
            if (uploadTask != null) {
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Log.d("CASSé", "TOUT CASSé")
                }.addOnSuccessListener { taskSnapshot ->
                    Log.d("QUOI", taskSnapshot.uploadSessionUri.toString())
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...

                }.addOnCompleteListener{
                    Log.d("QUOI", it.result.uploadSessionUri.toString())
                }
            }



        }

    }

    fun selectImage() {
        getContent.launch("image/*")
    }

}