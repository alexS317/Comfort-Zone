package com.example.ws2022creativecodelab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ws2022creativecodelab3.databinding.ActivityGalleryBinding
import com.google.android.material.tabs.TabLayout

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var navController: NavController
//    private val myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.tabBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (binding.tabBar.selectedTabPosition) {
                    0 -> navController.navigate(R.id.characterFragment)
                    else -> navController.navigate(R.id.affirmationFragment)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        binding.addCharacterButton.setOnClickListener { goToAddCharacterView() }
    }


//    override fun onResume() {
//        super.onResume()
//        val characters = myDB.getAllCharacters()
//        binding.characterGallery.adapter =
//            CharacterAdapter(this, characters.ids, characters.images)
//
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun goToAddCharacterView() {
        startActivity(Intent(this, AddCharacterActivity::class.java))
    }
}