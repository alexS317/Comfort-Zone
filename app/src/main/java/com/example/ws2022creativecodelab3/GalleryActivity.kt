package com.example.ws2022creativecodelab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ws2022creativecodelab3.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.addCharacterButton.setOnClickListener { goToAddCharacterView() }
    }

    override fun onResume() {
        super.onResume()
        val characters = myDB.getAllCharacters()
        binding.characterGallery.adapter =
            CharacterAdapter(this, characters.ids, characters.images)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun goToAddCharacterView() {
        startActivity(Intent(this, AddCharacterActivity::class.java))
    }
}