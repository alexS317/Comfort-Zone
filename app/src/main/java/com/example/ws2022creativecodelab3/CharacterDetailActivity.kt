package com.example.ws2022creativecodelab3

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.ws2022creativecodelab3.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.editButton.setOnClickListener { goToUpdateView() }
        binding.deleteButton.setOnClickListener { deleteDialog() }
    }

    override fun onResume() {
        super.onResume()
        val character = myDB.getOneCharacter(intent.getStringExtra("id").toString())
        val bitmap = BitmapFactory.decodeByteArray(character.image, 0, character.image.size)    // convert byteArray to image bitmap

        binding.characterImage.setImageBitmap(bitmap)
        binding.characterName.text = character.name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun goToUpdateView() {
        val updateView = Intent(this, AddCharacterActivity::class.java)
        updateView.putExtra("id", intent.getStringExtra("id").toString())
        startActivity(updateView)
    }

    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Character")
        builder.setMessage("Do you really want to delete this character entry?")

        builder.setPositiveButton("Yes") { dialog, which ->
            myDB.deleteCharacter(intent.getStringExtra("id").toString())
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}