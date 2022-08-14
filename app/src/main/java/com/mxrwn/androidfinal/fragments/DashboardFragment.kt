package com.mxrwn.androidfinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.RecyclerAdapter
import com.mxrwn.androidfinal.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {
    private lateinit var _binding: FragmentDashboardBinding;
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>

    private val binding get() = _binding!!
    var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutManager = LinearLayoutManager(this.context)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)



        db.collection("places")
            .get()
            .addOnSuccessListener() { document ->
                binding.recyclerView.layoutManager = layoutManager
                adapter = RecyclerAdapter(document.documents)
                binding.recyclerView.adapter = adapter
            }
        binding.mapToggle.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_mapsFragment2)
        }
        return binding.root
    }


}