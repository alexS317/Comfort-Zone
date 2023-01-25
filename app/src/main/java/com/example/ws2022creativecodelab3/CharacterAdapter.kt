package com.example.ws2022creativecodelab3

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(
    private val characterIds: MutableList<String>,
    private val characterImages: MutableList<ByteArray>,
    private val clickedCharacter: (String) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterId = characterIds[position]
        val characterImage = characterImages[position]
        val bitmap = BitmapFactory.decodeByteArray(
            characterImage,
            0,
            characterImage.size
        )  // convert byteArray to image bitmap

        holder.image.setImageBitmap(bitmap)

        holder.itemView.setOnClickListener { clickedCharacter(characterId) }
    }

    override fun getItemCount(): Int {
        return characterIds.size
    }
}