package com.example.ws2022creativecodelab3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AffirmationAdapter(
    private val context: Context,
    private val affirmations: MutableList<String>
) : RecyclerView.Adapter<AffirmationAdapter.AffirmationViewHolder>() {

    class AffirmationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val affirmationText: TextView = view.findViewById(R.id.affirmation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffirmationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.affirmation_item, parent, false)
        return AffirmationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AffirmationViewHolder, position: Int) {
        val affirmation = affirmations[position]

        holder.affirmationText.text = affirmation
    }

    override fun getItemCount(): Int {
        return affirmations.size
    }
}