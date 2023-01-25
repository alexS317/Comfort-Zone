package com.example.ws2022creativecodelab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ws2022creativecodelab3.databinding.ActivityGalleryBinding
import com.google.android.material.tabs.TabLayout

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var navController: NavController
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Listen which tab is currently selected and display the according fragment
        binding.tabBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (binding.tabBar.selectedTabPosition) {
                    0 -> {
                        navController.navigate(R.id.characterFragment)
                    }
                    else -> {
                        navController.navigate(R.id.affirmationFragment)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        binding.addButton.setOnClickListener {
            val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            val fragmentName = currentFragment!!.javaClass.simpleName

            if (fragmentName == "CharacterFragment") goToAddCharacterView()
            else goToAddAffirmationView()
        }

        characterViewModel.wasClicked.observe(this, Observer { id ->
            val detailView = Intent(this, CharacterDetailActivity::class.java)
            detailView.putExtra("id", id)
            startActivity(detailView)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun goToAddCharacterView() {
        startActivity(Intent(this, AddCharacterActivity::class.java))
    }

    private fun goToAddAffirmationView() {
        startActivity(Intent(this, AddAffirmationActivity::class.java))
    }
}