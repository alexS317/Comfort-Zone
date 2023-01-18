package com.example.ws2022creativecodelab3

import android.content.Intent
import android.graphics.BitmapFactory
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
        supportActionBar?.hide()

        getRandomCharacter()

        binding.affirmationButton.setOnClickListener { getRandomCharacter() }
        binding.galleryMenu.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }
    }

    private fun getRandomCharacter() {
        val characters = myDB.getAllCharacters()

        if (characters.ids.size == 0) {
            startActivity(Intent(this, AddCharacterActivity::class.java))
        } else {
        val randomId = characters.ids.random()
        val randomCharacter = myDB.getOneCharacter(randomId)
        val bitmap =
            BitmapFactory.decodeByteArray(randomCharacter.image, 0, randomCharacter.image.size)

        val affirmations = resources.getStringArray(R.array.default_affirmations)
        val randomAffirmation = affirmations.random()

        binding.affirmationImage.setImageBitmap(bitmap)
        binding.affirmationName.text = randomCharacter.name
        binding.affirmationText.text = randomAffirmation
        }
    }

}