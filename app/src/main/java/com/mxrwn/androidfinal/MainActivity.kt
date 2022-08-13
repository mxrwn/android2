package com.mxrwn.androidfinal

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.mxrwn.androidfinal.databinding.ActivityMainBinding
import com.mxrwn.androidfinal.fragments.AddFragment
import com.mxrwn.androidfinal.fragments.DashboardFragment
import com.mxrwn.androidfinal.fragments.MapsFragment
import com.mxrwn.androidfinal.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val dashboardFragment = DashboardFragment()
    private val addFragment = AddFragment()
    private val profileFragment = ProfileFragment()
    private val mapFragment = MapsFragment();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        print("test")
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val email = currentUser?.email
        Log.d("MAIN", email!!)
        binding = ActivityMainBinding.inflate(layoutInflater)
        replaceFragment(dashboardFragment)


        setContentView(binding.root)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_add -> replaceFragment(addFragment)
                R.id.ic_profile -> replaceFragment(profileFragment)
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
            }
            true
        }


    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}