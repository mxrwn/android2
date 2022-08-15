package com.mxrwn.androidfinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mxrwn.androidfinal.DetailFragmentArgs
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.RecyclerAdapter
import com.mxrwn.androidfinal.databinding.FragmentDashboardBinding
import com.mxrwn.androidfinal.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var _binding: FragmentDetailBinding;
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentDetailBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val bundle = arguments
        //val args = bundle?.let { DetailFragmentArgs.fromBundle(it) }
        //Log.d("ARGS", args?.place.toString())
    }


}