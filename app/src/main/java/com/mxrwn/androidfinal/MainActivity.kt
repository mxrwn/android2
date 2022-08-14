package com.mxrwn.androidfinal

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mxrwn.androidfinal.databinding.ActivityMainBinding
import com.mxrwn.androidfinal.fragments.AddFragment
import com.mxrwn.androidfinal.fragments.DashboardFragment
import com.mxrwn.androidfinal.fragments.MapsFragment
import com.mxrwn.androidfinal.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
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
        setContentView(R.layout.activity_main)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //replaceFragment(dashboardFragment)

        val navController = Navigation.findNavController(this, R.id.fragment_container);
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNav, navController);




    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}