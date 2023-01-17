package com.example.ws2022creativecodelab3

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ws2022creativecodelab3.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val character = myDB.getOneCharacter(intent.getStringExtra("id").toString())
        val bitmap = BitmapFactory.decodeByteArray(character.image, 0, character.image.size)

        binding.characterImage.setImageBitmap(bitmap)
        binding.characterName.text = character.name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}