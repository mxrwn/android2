package com.mxrwn.androidfinal.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.RecyclerAdapter
import com.mxrwn.androidfinal.databinding.FragmentDashboardBinding
import com.mxrwn.androidfinal.databinding.FragmentMyPlacesBinding


class MyPlacesFragment : Fragment() {
    private lateinit var _binding: FragmentMyPlacesBinding;
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutManager = LinearLayoutManager(this.context)

        _binding = FragmentMyPlacesBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db.collection("places")
            .whereEqualTo("user", auth.currentUser?.email)
            .get()
            .addOnSuccessListener() { document ->
                binding.recyclerView.layoutManager = layoutManager
                adapter = context?.let { RecyclerAdapter(document.documents, it) }!!
                binding.recyclerView.adapter = adapter
            }

        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_myPlacesFragment_to_profile)

        }
        return binding.root
    }
}