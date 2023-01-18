package com.example.ws2022creativecodelab3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.drawToBitmap
import com.example.ws2022creativecodelab3.databinding.ActivityAddCharacterBinding
import java.io.ByteArrayOutputStream

class AddCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCharacterBinding
    private val myDB = DatabaseHandler(this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.imageInput.setOnClickListener {
            val phoneGallery = Intent(Intent.ACTION_PICK, Media.INTERNAL_CONTENT_URI)
            resultLauncher.launch(phoneGallery)
        }

        if (intent.getStringExtra("id") != null) {
            val character = myDB.getOneCharacter(intent.getStringExtra("id").toString())
            val bitmap = BitmapFactory.decodeByteArray(character.image, 0, character.image.size)

            binding.imageInput.setImageBitmap(bitmap)
            binding.nameInput.setText(character.name)

            binding.saveButton.setOnClickListener { updateOldCharacter() }
        } else {
            binding.saveButton.setOnClickListener { saveNewCharacter() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                binding.imageInput.setImageURI(data?.data)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveNewCharacter() {
        val bitmap = binding.imageInput.drawToBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = stream.toByteArray()
        val name = binding.nameInput.text.toString()

        if (!binding.imageInput.drawable.isFilterBitmap || name == "") {
            Toast.makeText(this, "Please enter a valid name and image.", Toast.LENGTH_SHORT).show()
        } else {
            myDB.addCharacter(image, name)
            finish()
        }
    }

    private fun updateOldCharacter() {
        val bitmap = binding.imageInput.drawToBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = stream.toByteArray()
        val name = binding.nameInput.text.toString()

        if (name == "") {
            Toast.makeText(this, "Please enter a valid name.", Toast.LENGTH_SHORT).show()
        } else {
            myDB.updateCharacter(intent.getStringExtra("id").toString(), image, name)
            finish()
        }

    }
}