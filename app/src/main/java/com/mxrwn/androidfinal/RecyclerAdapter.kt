package com.mxrwn.androidfinal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.mxrwn.androidfinal.fragments.DashboardFragmentDirections


class RecyclerAdapter(private val dataSet: MutableList<DocumentSnapshot>, private val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var itemImage: ImageView
        lateinit var itemTitle: TextView
        lateinit var itemDetail: TextView

        init {
            // Define click listener for the ViewHolder's View.
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemTitle.text = dataSet[position]["place"].toString()
        holder.itemDetail.text = dataSet[position]["user"].toString()
        if(dataSet[position]["image"] != null){
            Glide.with(context).load(dataSet[position]["image"]).into(holder.itemImage);

        }
        holder.itemTitle.setOnClickListener {

            //val bundle = bundleOf(Pair("place", holder.itemTitle.text.toString()),
            //    Pair("user", holder.itemDetail.text.toString()))

            val directions = DashboardFragmentDirections.actionDashboardToDetailFragment("yredsy")
            it.findNavController().navigate(directions)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}