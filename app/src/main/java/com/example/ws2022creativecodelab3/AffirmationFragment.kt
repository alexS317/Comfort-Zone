package com.example.ws2022creativecodelab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ws2022creativecodelab3.databinding.FragmentAffirmationBinding

class AffirmationFragment : Fragment() {

    private lateinit var binding: FragmentAffirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAffirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val myDB = DatabaseHandler(requireContext())

        val defaultAffirmations =
            resources.getStringArray(R.array.default_affirmations).toMutableList()
        binding.defaultAffirmations.adapter =
            AffirmationAdapter(requireContext(), defaultAffirmations, defaultAffirmations)

        val customAffirmations = myDB.getAllAffirmations()
        binding.customAffirmations.adapter = AffirmationAdapter(
            requireContext(),
            customAffirmations.first,
            customAffirmations.second
        )
    }
}