package com.example.ws2022creativecodelab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ws2022creativecodelab3.databinding.ActivityAddAffirmationBinding

class AddAffirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAffirmationBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButton.setOnClickListener { saveNewAffirmation() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveNewAffirmation() {
        val text = binding.affirmationInput.text.toString()
        myDB.addAffirmation(text)
        finish()
    }
}