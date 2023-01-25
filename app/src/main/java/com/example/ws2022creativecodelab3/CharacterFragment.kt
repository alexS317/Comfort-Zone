package com.example.ws2022creativecodelab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ws2022creativecodelab3.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private val characterViewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val myDB = DatabaseHandler(requireContext())

        val characters = myDB.getAllCharacters()
        binding.characterGallery.adapter =
            CharacterAdapter(characters.ids, characters.images) { id ->
                characterViewModel.characterClicked(id)
            }
    }
}