package com.example.ws2022creativecodelab3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import com.example.ws2022creativecodelab3.databinding.ActivityAddCharacterBinding
import java.io.ByteArrayOutputStream

class AddCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCharacterBinding
    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.imageInput.setOnClickListener {
            val phoneGallery = Intent(Intent.ACTION_PICK, Media.INTERNAL_CONTENT_URI)
            resultLauncher.launch(phoneGallery)
        }

        binding.saveButton.setOnClickListener { saveNewCharacter() }
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

    private fun saveNewCharacter() {
        val bitmap = binding.imageInput.drawToBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = stream.toByteArray()
        val name = binding.nameInput.text.toString()

        myDB.addCharacter(image, name)
        finish()
    }
}