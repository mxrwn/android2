package com.mxrwn.androidfinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.RecyclerAdapter
import com.mxrwn.androidfinal.databinding.FragmentDashboardBinding
import com.mxrwn.androidfinal.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var _binding: FragmentProfileBinding;
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profile_to_myPlacesFragment)
        }

        binding.button2.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_profile_to_languagesFragment)
        }

        return binding.root
    }
}