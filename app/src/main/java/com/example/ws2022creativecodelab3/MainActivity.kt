package com.example.ws2022creativecodelab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ws2022creativecodelab3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val characters = myDB.getAllCharacters()
//        binding.characterGallery.adapter = CharacterAdapter(this, characters.ids, characters.images, characters.names)

        binding.addCharacterButton.setOnClickListener { goToAddCharacterView() }
    }

    override fun onResume() {
        super.onResume()
        val characters = myDB.getAllCharacters()
        binding.characterGallery.adapter =
            CharacterAdapter(this, characters.ids, characters.images, characters.names)

    }

    private fun goToAddCharacterView() {
        startActivity(Intent(this, AddCharacterActivity::class.java))
    }
}