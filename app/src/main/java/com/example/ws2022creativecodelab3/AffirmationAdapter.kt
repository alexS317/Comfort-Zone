package com.example.ws2022creativecodelab3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class AffirmationAdapter(
    private val context: Context,
    private val affirmationIds: MutableList<String>,
    private val affirmations: MutableList<String>
) : RecyclerView.Adapter<AffirmationAdapter.AffirmationViewHolder>() {

    private val myDB = DatabaseHandler(context)
    private val defaultAffirmations =
        context.resources.getStringArray(R.array.default_affirmations).toMutableList()

    class AffirmationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val affirmationText: TextView = view.findViewById(R.id.affirmation)
        val affirmationDelete: ImageView = view.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffirmationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.affirmation_item, parent, false)
        return AffirmationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AffirmationViewHolder, position: Int) {
        val affirmationId = affirmationIds[position]
        val affirmation = affirmations[position]

        holder.affirmationText.text = affirmation
        holder.affirmationDelete.visibility = View.INVISIBLE

        // Make affirmations only clickable if they have an id in the database (only custom affirmations)
        // For predefined affirmations, id and text are the same value
        if (affirmationIds != defaultAffirmations) {
            holder.affirmationDelete.visibility =
                View.VISIBLE  // Delete button only visible for custom affirmations

            holder.itemView.setOnClickListener {
                deleteAffirmationDialog(affirmationId)
            }
        }

    }

    override fun getItemCount(): Int {
        return affirmations.size
    }

    private fun deleteAffirmationDialog(id: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Affirmation")
        builder.setMessage("Do you really want to delete this affirmation entry?")

        builder.setPositiveButton("Yes") { dialog, which ->
            myDB.deleteAffirmation(id)
            Toast.makeText(context, "Affirmation deleted.", Toast.LENGTH_SHORT).show()
            AffirmationFragment().onResume()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}